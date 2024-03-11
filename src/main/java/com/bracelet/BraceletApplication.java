package com.bracelet;

import com.bracelet.socket.GetPicAndSoundSocket;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ServletComponentScan
public class BraceletApplication {

    public static void main(String[] args) {
         SpringApplication.run(BraceletApplication.class, args);
        /*SpringApplication springApplication = new SpringApplication(BraceletApplication.class);
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        GetPicAndSoundSocket.setApplicationContext(configurableApplicationContext);*/
    }

}

