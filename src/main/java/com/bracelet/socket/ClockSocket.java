package com.bracelet.socket;


import com.bracelet.service.BraceletUserService;
import com.bracelet.service.MessageService;
import com.bracelet.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Component
@Repository
public class ClockSocket {
    //接收求救者发来的位置
    @Autowired
    private RouteService routeService;

    private static MessageService messageService;
    @Autowired
    public void setMessageService(MessageService messageService){
        ClockSocket.messageService = messageService;
    }
    ServerSocket serverSocket;



    private static BraceletUserService braceletUserService;

    @Autowired
    public void setBraceletUserService(BraceletUserService braceletUserService){
        ClockSocket.braceletUserService = braceletUserService;
    }

//        BufferedReader bufferedReader;

    InputStream inputStream;

    PrintWriter printWriter;

    static String receiveMessage;

    static String oldMessage;

    Socket socket = null;

    public ClockSocket(){

        try {
            serverSocket = new ServerSocket(28888);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("启动服务器......" + 28888);
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
//                        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        printWriter = new PrintWriter(socket.getOutputStream());
                        //  printWriter.write("aaaa");
                        // printWriter.flush();
                        inputStream = socket.getInputStream();
                        getMessageFromClient = new GetMessageFromClient(printWriter,inputStream);
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
        InputStream inputStream;
        PrintWriter printWriter;
        public GetMessageFromClient(PrintWriter printWriter,InputStream inputStream){
            this.printWriter = printWriter;
            this.inputStream = inputStream;
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
                if(receive.indexOf("phone:") != -1){
                    int i =  receive.indexOf("phone:");
                    String phone = receive.substring(i + "phone:".length());
                //    System.out.println(phone);
                    uid = braceletUserService.getUid(phone.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("bbbbbb");
      //      while(this.isAlive()){
                try{
                    for (int i = 0;i < 5; ++i){
                        printWriter.println("alarm12019,08,28,14,35,302019,08,28,14,35,402019,08,28,14,35,50");
                        printWriter.flush();
                        Thread.sleep(200);
                    }

                    // while(RedisUtil.get(uid + "") == null);
                    //RedisUtil.del(uid + "");
                    /*System.out.println("cccccc");
                    printWriter.println("check");
                    printWriter.flush();
                    Thread.sleep(100);
                    int t = 0;
                    while(inputStream.available() <= 0 && t < 20){
                        printWriter.println("check");
                        printWriter.flush();
                        t++;
                        Thread.sleep(100);
                    }*/
                    // if(t >= 20)continue;
                //   while(inputStream.available() > 0){
                     /*   System.out.println("aaaaaaa");
                        data = new byte[inputStream.available()];
                        inputStream.read(data);
                        String receive = new String(data);
                        System.out.println(receive);
                        RedisUtil.set("data-" + uid,receive);*/
                        /**
                         * location:phone:123,lat:12.34,log:56.78
                         */
                      /*  if(receiveMessage.indexOf("rate:") != -1){
                            int phoneIndex = receive.indexOf("phone:") + 6;
                            int mbpIndex = receive.indexOf("mbp:") + 4;
                            int tempIndex = receive.indexOf("temper:") + 7;
                            String phone = receive.substring(phoneIndex, receive.indexOf("mbp:"));
                            String temper = receive.substring(tempIndex,mbpIndex);
                            Integer mbp = Integer.parseInt(receive.substring(mbpIndex));
                           // Integer max = Integer.parseInt(RedisUtil.get("rateMax-" + phone));
                           // Integer min = Integer.parseInt(RedisUtil.get("rateMin-" + phone));
                           // max = max == null ? mbp : max > mbp ? max: mbp;
                          //  min = min == null ? mbp : min < mbp ? min: mbp;
                            if (uid == null)uid = braceletUserService.getUid(phone);
                          //  RedisUtil.set("rateMax-" + uid,max.toString());
                           // RedisUtil.set("rateMin-" + uid,max.toString());
                            RedisUtil.set("heart-" + uid,mbp.toString());
                            RedisUtil.set("temper-"+uid,temper);
                            *//* String openId = messageService.getopendidByTel(phone);
                            System.out.println(openId);
                            if(openId == null)continue;
                            double lat = Double.parseDouble(receive.substring(latIndex, receive.indexOf("log:")));
                            double log = Double.parseDouble(receive.substring(logIndex));
                            routeService.saveRoute(openId, lat, log);*//*
                        }*/
                   // }
                }catch (Exception e){
//                    e.printStackTrace();
                    System.out.println("客户端连接已断开");
                    if(socket != null){
                        try {
                            socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }finally {
                            socket = null;
                        }
                    }
          //          break;
                }/* catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
         //   }
        }

    }

}
