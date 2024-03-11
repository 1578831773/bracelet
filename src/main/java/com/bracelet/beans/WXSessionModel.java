package com.bracelet.beans;

public class WXSessionModel
{
    //保存微信登录后返回的openid和session_key
    private String session_key;
    private String openid;

    public String getSession_key()
    {
        return this.session_key;
    }

    public void setSession_key(String session_key)
    {
        this.session_key = session_key;
    }

    public String getOpenid()
    {
        return this.openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }
}
