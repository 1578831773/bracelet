package com.bracelet.beans;

public class SoundUrl {
    //音频的url
    private String openid;

    private String soundUrl;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public SoundUrl(String openid, String soundUrl) {
        this.openid = openid;
        this.soundUrl = soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl == null ? null : soundUrl.trim();
    }
}