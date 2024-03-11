package com.bracelet.socket;/*
package com.bracelet.socket;

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
public class MySocket {
    //接收求救者发来的位置
    @Autowired
    private RouteService routeService;

    private static MessageService messageService;
    @Autowired
    public void setMessageService(MessageService messageService){
        MySocket.messageService = messageService;
    }
    ServerSocket serverSocket;

//        BufferedReader bufferedReader;

    InputStream inputStream;

    PrintWriter printWriter;

    static String receiveMessage;

    static String oldMessage;

    Socket socket = null;

    public MySocket(){

        try {
            serverSocket = new ServerSocket(28888);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("启动服务器......");
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
                        inputStream = socket.getInputStream();
                        getMessageFromClient = new GetMessageFromClient(inputStream);
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

        InputStream inputStream;

        public GetMessageFromClient(InputStream inputStream){
            this.inputStream = inputStream;
        }

        public void run(){
            while(this.isAlive()){
                try{
                    while(inputStream.available() > 0){
                        byte[] data = new byte[inputStream.available()];
                        inputStream.read(data);
                        String receive = new String(data);
                        System.out.println(receive);
                        receiveMessage = receive;
                        if(!receiveMessage.equals("")){
                            oldMessage = receiveMessage;
                        }
                        */
/**
                         * location:phone:123,lat:12.34,log:56.78
                         *//*

                        if(receiveMessage.indexOf("location:") != -1){
                            int idIndex = receive.indexOf("phone:") + 6;
                            int latIndex = receive.indexOf("lat:") + 4;
                            int logIndex = receive.indexOf("log:") + 4;
                            String phone = receive.substring(idIndex, receive.indexOf("lat:"));
                            String openId = messageService.getopendidByTel(phone);
                            System.out.println(openId);
                            if(openId == null)continue;
                            double lat = Double.parseDouble(receive.substring(latIndex, receive.indexOf("log:")));
                            double log = Double.parseDouble(receive.substring(logIndex));
                            routeService.saveRoute(openId, lat, log);
                        }
                    }
                }catch (IOException e){
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
                    break;
                }
            }
        }

    }

}
*/
