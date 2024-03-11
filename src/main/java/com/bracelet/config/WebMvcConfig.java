

package com.bracelet.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    /*
     * 添加静态资源文件，外部可以直接访问地址
     *
     * @param registry
     */


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("file:/usr/pictures/");
        registry.addResourceHandler("/sounds/**").addResourceLocations("file:/usr/sounds/");
    }
}






