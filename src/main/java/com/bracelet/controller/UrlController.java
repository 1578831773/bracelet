package com.bracelet.controller;

import com.bracelet.beans.Route;
import com.bracelet.beans.SimpleRoute;
import com.bracelet.service.RouteService;
import com.bracelet.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UrlController {
    @Autowired
    UrlService urlService;
    @Autowired
    RouteService routeService;
   // @PostMapping("/getPicUrl")
    public String getPicUrl(String openid){
        List<String> list = urlService.getPicUrl(openid);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i < list.size() - 1; ++i)
            stringBuilder.append(list.get(i) + "&&");
        if(list.size() > 0)
           stringBuilder.append(list.get(list.size() - 1));
        return stringBuilder.toString();
    }
   // @PostMapping("/getSoundUrl")
    public String getSoundUrl(String openid){
        List<String> list = urlService.getSoundUrl(openid);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0;i < list.size() - 1; ++i)
            stringBuilder.append(list.get(i) + "&&");
        if(list.size() > 0)
            stringBuilder.append(list.get(list.size() - 1));
        return stringBuilder.toString();
    }

    /**
     * 返回求救者的发送的求救信息
     * @param openid 求助者的openid
     * @return 求救者的求救信息
     */
    @PostMapping({"/getMessage"})
    public Map<String,Object> getMessage(String openid){
        Map<String,Object> map = new HashMap<>();
        map.put("picUrl",getPicUrl(openid));
        map.put("soundUrl",getSoundUrl(openid));
        ArrayList<Route> routes = routeService.getRoute(openid);
        ArrayList<SimpleRoute> simpleRoutes = new ArrayList<>();
        for (Route route: routes){
            simpleRoutes.add(new SimpleRoute(route.getLat(),route.getLog()));
        }
        map.put("route",simpleRoutes);
        return map;
    }

}
