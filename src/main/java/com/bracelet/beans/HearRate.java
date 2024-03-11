package com.bracelet.beans;

public class HearRate {
    private int hid;
    private int uid;
    private int maxRate;
    private int minRate;
    private String date;

    public HearRate(int uid, int maxRate, int minRate, String date) {
        this.uid = uid;
        this.maxRate = maxRate;
        this.minRate = minRate;
        this.date = date;
    }
    public HearRate() {
    }

    @Override
    public String toString() {
        return "HearRate{" +
                "hid=" + hid +
                ", uid=" + uid +
                ", maxRate='" + maxRate + '\'' +
                ", minRate='" + minRate + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(int maxRate) {
        this.maxRate = maxRate;
    }

    public int getMinRate() {
        return minRate;
    }

    public void setMinRate(int minRate) {
        this.minRate = minRate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
