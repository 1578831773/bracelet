package com.bracelet.websocket;


import com.bracelet.beans.Msg;
import com.bracelet.service.BraceletUserService;
import com.bracelet.service.MessageService;
import com.bracelet.utils.JsonUtil;
import com.bracelet.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@ServerEndpoint(value = "/websocket/{id}")
@Component
public class MyWebSocket {

    private static Map<String, Session> map = new HashMap<>();
    private static CopyOnWriteArrayList<MyWebSocket> webSocketset = new CopyOnWriteArrayList<MyWebSocket>();
    private Session session;
    private String id;

    private static BraceletUserService braceletUserService;
    @Autowired
    public  void setBraceletUserService(BraceletUserService braceletUserService) {
        this.braceletUserService = braceletUserService;
    }

    @Autowired
    private static MessageService messageService;

    @Autowired
    public  void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public String getId(){
        return this.id;
    }
    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("id") String id){
        this.session = session;
        this.id = id;
        map.put(this.id,session);
        webSocketset.add(this);
        System.out.println("有新连接加入！id为:" + id + ", 当前在线人数为" + webSocketset.size());
        this.session.getAsyncRemote().sendText(this.id +"上线了"+"（他的频道号是"+session.getId()+"）");
    }
    @OnClose
    public void onClose(){
        map.remove(this.session.getId());
        webSocketset.remove(this);
        System.out.println("有一连接关闭！当前在线人数为" + webSocketset.size());
    }
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }
    @OnMessage
    public void onMessage(Session session, String message){
        System.out.println("来自客户端的消息:" + message);
        sendInfo(session,message);
    }
    public  void sendInfo(Session session,String info){
            Msg msg = new Msg();
            msg.setCode(2);
            try{
                System.out.println("openid" + id);
                System.out.println("info"+info);
                String phone = (String) messageService.getUserInfo(id).get("phone");
                System.out.println("phone" + phone);
                Integer uid = braceletUserService.getUid(phone.trim());
                if(info.indexOf("clock") != -1){
                    RedisUtil.set(uid + "clock",info);
                    return;
                }
                RedisUtil.set(uid + "","need");
                System.out.println("uid" + uid);
                String str = "";
                int sum = 0;
                //valid:00visible:08longitude:0.00000latitude:0.00000roughlatitude:roughlongitroughlongitude:temper:35.42pressure:phone:13875110242
                while(sum < 200){
                    Thread.sleep(100);
                    if(RedisUtil.get("data-" + uid) != null)break;
                    sum = sum + 10;
                }
                RedisUtil.del(uid + "");
                if(sum < 200){
                    msg.setCode(1);
                    String receive = RedisUtil.get("data-" + uid);
                    RedisUtil.del("data-" + uid);
                    if(info.equals("location")){
                        System.out.println(receive);
                        int latIndex = receive.indexOf("roughlatitude:") + 14;
                        int logIndex = receive.indexOf("roughlongitude:") + 15;
                        int end = receive.indexOf("temper:");
                        if(latIndex  != logIndex && logIndex  != end && latIndex != -1 && logIndex != -1){
                            msg.getExtend().put("lat",receive.substring(latIndex,logIndex - 15));
                            msg.getExtend().put("log",receive.substring(logIndex,end));
                        }else {
                            msg.setCode(2);
                        }
                    }else if(info.equals("temper")) {
                        int temperIndex = receive.indexOf("temper:") + 7;
                        int end = receive.indexOf("phone:");
                        if(temperIndex  != end && temperIndex != -1){
                            msg.setMessage(receive.substring(temperIndex,end));
                        }else {
                            msg.setCode(2);
                        }
                    }else if(info.equals("pressure")) {
                        int lowAvg = 0,highAvg = 0;
                        int pressureIndex = receive.indexOf("pressure:") + 9;
                        int end = receive.length();
                        int count = 0;
                        if(pressureIndex != -1) {
                            while(pressureIndex != end){
                                count ++;
                                lowAvg  += Integer.parseInt(receive.substring(pressureIndex,pressureIndex + 3));
                                pressureIndex += 3;
                                highAvg  += Integer.parseInt(receive.substring(pressureIndex,pressureIndex + 3));
                                pressureIndex += 3;
                            }
                            if(count != 0){
                                lowAvg /= count;
                                highAvg /= count;
                            }
                            msg.getExtend().put("high", highAvg);
                            msg.getExtend().put("low", lowAvg);
                        }
                    }else if(info.equals("heart")){
                        int end = receive.indexOf("valid");
                        if (end!=-1){
                            msg.setMessage(receive.substring(0,end));
                        }
                    } else {
                        String match = "";
                        if(info.equals("heart"))match = "mbp:";
                        else if(info.equals("temper"))match = "temper:";
                        int i = receive.indexOf(match) + match.length();
                        int dt = 0;
                        for (;i < receive.length(); ++i){
                            dt = dt * 10 + dt - '0';
                        }
                        str = dt + "";
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            String json = JsonUtil.toJson(msg);
            try {
                session.getBasicRemote().sendText(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
