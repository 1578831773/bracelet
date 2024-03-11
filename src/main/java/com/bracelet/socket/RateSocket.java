package com.bracelet.socket;


import com.bracelet.service.BraceletUserService;
import com.bracelet.service.MessageService;
import com.bracelet.service.RouteService;
import com.bracelet.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Repository
public class RateSocket {
    //接收求救者发来的位置
    @Autowired
    private RouteService routeService;

    private static MessageService messageService;
    private Map<Integer,GetMessageFromClient> threadMap = new HashMap<>();
    private Map<GetMessageFromClient, Boolean> runMap = new HashMap<>();
    @Autowired
    public void setMessageService(MessageService messageService){
        RateSocket.messageService = messageService;
    }
    ServerSocket serverSocket;



    private static BraceletUserService braceletUserService;

    @Autowired
    public void setBraceletUserService(BraceletUserService braceletUserService){
           RateSocket.braceletUserService = braceletUserService;
    }

//        BufferedReader bufferedReader;

    InputStream inputStream;

    PrintWriter printWriter;

    static String receiveMessage;

    static String oldMessage;

    Socket socket = null;

    public RateSocket(){

        try {
            serverSocket = new ServerSocket(48888);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("启动服务器......" + 48888);
        new AcceptSocketThread().start();

    }

    public static String getReceiveMessage() {
        return receiveMessage;
    }

    public static String getOldMessage() {
        return oldMessage;
    }

    public Socket getSocket() {
        return socket;
    }

    public void sendMessage(){
        String str = "sendMessage-->";
        printWriter.print(str);
        printWriter.flush();
        System.out.println("已向客户端发送： " + str);
    }


    class AcceptSocketThread extends Thread{

        GetMessageFromClient getMessageFromClient;

        public void run(){

            if(this.isAlive()){
                System.out.println("it is alive......");
            }
            while(this.isAlive()){
                try{
                    socket = serverSocket.accept();
                    if(socket != null){
                        System.out.println("添加一个客户端连接");
                        getMessageFromClient = new GetMessageFromClient(socket);
                        getMessageFromClient.start();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    break;
                }
            }
        }

    }

    class GetMessageFromClient extends Thread{
        Integer uid ;
        Socket socket;
        InputStream inputStream;
        PrintWriter printWriter;
        public GetMessageFromClient(Socket socket){
            this.socket = socket;
            try {
                this.printWriter = new PrintWriter(socket.getOutputStream());
                this.inputStream = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    socket.close();
                    System.out.println("关闭连接");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }

        public void run(){
            while(true){
                try {
                    if (!(inputStream.available() <= 0)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            byte[] data = null;
            try {
                data = new byte[inputStream.available()];
                inputStream.read(data);
                String receive = new String(data);
                if(receive.contains("phone:")){
                    System.out.println(receive);
                    int i =  receive.indexOf("phone:");
                    String phone = receive.substring(i + "phone:".length(),i + "phone:".length() + 11);
                    System.out.println(phone);
                    uid = braceletUserService.getUid(phone.trim());
                    System.out.println("uid:"+uid);
                    GetMessageFromClient thread = threadMap.get(uid);
                    if(thread != null){
                        if(runMap.get(thread)){
                            runMap.replace(thread,false);
                            threadMap.remove(uid);
                        }
                    }
                    threadMap.put(uid,this);
                    runMap.put(this,true);
                }
                while(runMap.get(this)){
                    try {
                        while(RedisUtil.get(uid + "") == null && RedisUtil.get(uid + "clock") == null);
                        if(RedisUtil.get(uid + "clock") != null){
                            System.out.println("clock");
                            String info = RedisUtil.get(uid + "clock");
                            String[] infos = null;
                            String partten = "0000,00,00,00,00,00";
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy,MM,dd");
                            System.out.println(info);
                            info = info.replace("clock","");
                            infos = info.split("-");
                            String time = "alarm1";
                            for (int i = 0;i < 3; ++i){
                                if(infos[i].length() == 1)time += partten;
                                    //else time +=  simpleDateFormat.format(new Date()) + "," + infos[i] + ",00";
                                else time +=  "2019,09,03" + "," + infos[i] + ",00";
                            }
                            for(int i = infos.length;i < 3; ++i)
                                time += partten;
                            System.out.println(time);
                            long result = RedisUtil.del(uid + "clock");
                            System.out.println(result);
                            printWriter.write(time);
                            printWriter.flush();
                            continue;
                        }
                        System.out.println("del "+uid);
                        RedisUtil.del(uid + "");
                        System.out.println("check");
                        printWriter.println("check");
                        printWriter.flush();
                        Thread.sleep(100);
                        int t = 0;
                        while(inputStream.available() <= 0 && t < 15){
                            printWriter.println("check");
                            printWriter.flush();
                            t++;
                            Thread.sleep(200);
                        }
                        if(t >= 20)continue;
                        if (inputStream.available() > 0){
                            data = new byte[inputStream.available()];
                            inputStream.read(data);
                            String receive1 = new String(data);
                            System.out.println(receive1);
                            RedisUtil.set("data-" + uid,receive1);
                            /**
                             * location:phone:123,lat:12.34,log:56.78
                             */
                        }
                    } catch (IOException | InterruptedException e) {
                        if(socket != null){
                            try {
                                socket.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }finally {
                                socket = null;
                            }
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            runMap.remove(this);
        }

    }

}
