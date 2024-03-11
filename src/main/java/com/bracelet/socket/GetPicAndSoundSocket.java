package com.bracelet.socket;


import com.bracelet.service.MessageService;
import com.bracelet.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

@Component
public class GetPicAndSoundSocket {
    ServerSocket serverSocket;
    //接收求救者发来的图片和录音


    private static UrlService UrlService;

    @Autowired
    public  void setPic_urlMapper(UrlService UrlService) {
        //System.out.println(pic_urlMapper1+ "aaaa");
        GetPicAndSoundSocket.UrlService = UrlService;
    }
    private static MessageService messageService;
    @Autowired
    public void setMessageService(MessageService messageService){
        GetPicAndSoundSocket.messageService = messageService;
    }
    public GetPicAndSoundSocket(){
        try {
            serverSocket = new ServerSocket(58888);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("启动服务器......");
        new AcceptSocketThread().start();
    }



    class AcceptSocketThread extends Thread{
        Socket socket;
        GetMessageFromClient getMessageFromClient;
        public void run(){
          //  System.out.println(pic_urlMapper);
            if(this.isAlive()){
                System.out.println("it is alive......");
            }
            while(this.isAlive()){
                try{
                    socket = serverSocket.accept();
                    if(socket != null){
                        System.out.println("添加一个客户端连接1");
//                        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

        Socket socket;
        public GetMessageFromClient(Socket socket){
            this.socket = socket;
        }
        //namewhytypejpgimromation
        public void run(){
            try{
            InputStream inputStream = socket.getInputStream();
            OutputStream aaaaaaa = socket.getOutputStream();
           // aaaaaaa.write("PN".getBytes());
           // aaaaaaa.write("1234560".getBytes());
            StringBuffer sb = new StringBuffer();
          /*  while(inputStream.available() < 5){
                byte[] bytes =new byte[1024];
                int len = inputStream.read(bytes);
                String s = new String(bytes, 0, len, "ISO-8859-1");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(s);
                System.out.println(stringBuilder.toString());
            }*/
           while(inputStream.available() < 100);
               // System.out.println(new String(0,));
            byte[] bytes =new byte[1024];
            int len = inputStream.read(bytes);
            String s = new String(bytes, 0, len, "ISO-8859-1");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(s);
            //System.out.println(stringBuilder);
            int i = stringBuilder.indexOf("name");
            int j = stringBuilder.indexOf("type");
            int k = stringBuilder.indexOf("imformation");
            String name = stringBuilder.substring(i + "name".length(),j);
            String type = stringBuilder.substring(j + "type".length(),k);
            String imformation = stringBuilder.substring(k + "imformation".length());

            Date date  = new Date();
            String openid = messageService.getopendidByTel(name);
            System.out.println(openid);
            if(openid == null)return;
            File file = null;
            if(type.equals("jpg")){
                UrlService.setPicUrl(openid,openid  + date.getTime()+"."+type);
                file = new File("/usr/pictures/",openid  + date.getTime()+"."+type );
            }else if(type.equals("mp3")){
                UrlService.setSoundUrl(openid,openid  + date.getTime()+"."+type);
                file = new File("/usr/sounds/",openid  + date.getTime()+"."+type);
            }else {
                return;
            }
            if(file == null)return;
            //File file = new File("/usr/pictures/",openid  + date.getTime()+"."+type );
            System.out.println(openid + "-" + type);
            //System.out.println(date);
            OutputStream  outputStream = new FileOutputStream(file);
            bytes = imformation.getBytes("ISO-8859-1");
            System.out.println(new String(bytes,0,bytes.length));
            outputStream.write(bytes,0,bytes.length);
            while((len = inputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
            }

            }catch (IOException e){
//                   e.printStackTrace();
                    System.out.println("客户端连接已断开");
                    if(this.socket != null){
                        try {
                            this.socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }finally {
                            socket = null;
                        }
                    }
                }
            }
        }

    }


