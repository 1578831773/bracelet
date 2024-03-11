package com.bracelet.controller;

import com.bracelet.beans.Msg;
import com.bracelet.beans.Route;
import com.bracelet.beans.SimpleRoute;
import com.bracelet.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RouteController {

    @Autowired
    private RouteService routeService;
    //返回指定openid的所有经过的位置
    @PostMapping("/getRoute")
    public Msg getRoute(String openid){
        System.out.println(openid);
        ArrayList<Route> routes = routeService.getRoute(openid);
        if(routes == null || routes.size() <= 0){
            return Msg.fail();
        }
        ArrayList<SimpleRoute> simpleRoutes = new ArrayList<>();
        for (Route route:routes){
            simpleRoutes.add(new SimpleRoute(route.getLat(),route.getLog()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("route", simpleRoutes);
        Msg msg = new Msg();
        msg.setCode(1);
        msg.setMessage("处理成功!");
        msg.setExtend(map);
        return msg;
    }

}
