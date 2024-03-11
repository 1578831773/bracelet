package com.bracelet.service;

import com.bracelet.beans.Route;
import com.bracelet.dao.RouteDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class RouteService {

    @Resource
    private RouteDao routeDao;

    /**
     * 将呼叫者的经纬度保存进数据库
     * @param openId 呼救者的openid
     * @param lat 呼救者的纬度
     * @param log 呼救者的纬度
     * @return 返回保存是否成功
     */
    public int saveRoute(String openId, double lat, double log){
        return routeDao.insert(new Route(openId, lat, log));
    }

    /**
     * 查询指定呼救者的所经过的位置
     * @param openId 呼救者的 openId
     * @return 返回呼救者所有位置的集合
     */
    public ArrayList<Route> getRoute(String openId){
        return routeDao.select(openId);
    }

}
