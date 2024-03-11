package com.bracelet.beans;

public class Route {

    //存放经纬度的对象
    private String openId;

    private double lat;

    private double log;

    public Route() {
    }

    public Route(String openId, double lat, double log) {
        this.openId = openId;
        this.lat = lat;
        this.log = log;
    }

    public String getUserId() {
        return openId;
    }

    public void setUserId(String openId) {
        this.openId = openId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }
}
