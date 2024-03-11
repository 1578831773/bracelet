package com.bracelet.interceptor;

import com.bracelet.beans.Token;
import com.bracelet.utils.JWTUtils;
import com.bracelet.utils.JsonUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Token token1 = JWTUtils.unsign(token, Token.class);
        Map<String,String > map = new HashMap<>();
        System.out.println(token1);
        if(token1 == null){
            map.put("error","1");
            response.getWriter().write(JsonUtils.objectToJson(map));
            return false;
        }
        System.out.println(token1);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
