package com.bracelet.config;/*
package com.bracelet.config;

import com.bracelet.interceptor.LoginInterceptor;
import com.bracelet.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       */
/* registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/login")
                .addPathPatterns("/register");*//*



        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/wxLogin");
    }
}
*/
