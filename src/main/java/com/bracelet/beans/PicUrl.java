package com.bracelet.beans;

public class PicUrl {
    //存放图片的url
    private String openid;

    private String picUrl;

    public PicUrl(String openid, String picUrl) {
        this.openid = openid;
        this.picUrl = picUrl;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }
}