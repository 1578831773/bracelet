
package com.bracelet.service;

import com.bracelet.beans.PicUrl;
import com.bracelet.beans.PicUrlExample;
import com.bracelet.beans.SoundUrl;
import com.bracelet.beans.SoundUrlExample;
import com.bracelet.dao.PicUrlMapper;
import com.bracelet.dao.SoundUrlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UrlService {
    @Autowired
    PicUrlMapper picUrlMapper;
    @Autowired
    SoundUrlMapper soundUrlMapper;

    /**
     * 获取呼救者发来的图片
     * @param opeid 呼救者的openid
     * @return 返回呼救者图片相对地址组成的字符串
     */
    public List<String> getPicUrl(String opeid){
        PicUrlExample picUrlExample = new PicUrlExample();
        PicUrlExample.Criteria criteria = picUrlExample.createCriteria();
        criteria.andOpenidEqualTo(opeid);
        List<PicUrl> list = picUrlMapper.selectByExample(picUrlExample);
        List<String> list1 = new ArrayList<>();
        for (PicUrl picUrl:list
             ) {
            list1.add(picUrl.getPicUrl());
        }
        return list1;
    }

    /**
     * 将呼救者传来的图片的名称保存在数据库
     * @param openid 呼救者的openid
     * @param url 图片的名称
     * @return 返回图片是否保存成功
     */
    public boolean setPicUrl(String openid,String url){
        PicUrl picUrl = new PicUrl(openid,url);
        int i = picUrlMapper.insertSelective(picUrl);
        if(i > 0)return true;
        return false;
    }
    /**
     * 将呼救者传来的录音的名称保存在数据库
     * @param openid 呼救者的openid
     * @param url 图片的名称
     * @return 返回录音是否保存成功
     */
    public boolean setSoundUrl(String openid,String url){
        SoundUrl soundUrl = new SoundUrl(openid,url);
        int i = soundUrlMapper.insertSelective(soundUrl);
        if(i > 0)return true;
        return false;
    }
    /**
     * 获取呼救者发来的录音
     * @param opeid 呼救者的openid
     * @return 返回呼救者录音相对地址组成的字符串
     */
    public List<String> getSoundUrl(String opeid){
        SoundUrlExample soundUrlExample = new SoundUrlExample();
        SoundUrlExample.Criteria criteria = soundUrlExample.createCriteria();
        criteria.andOpenidEqualTo(opeid);
        List<SoundUrl> list = soundUrlMapper.selectByExample(soundUrlExample);
        List<String> list1 = new ArrayList<>();
        for (SoundUrl soundUrl:list
        ) {
            list1.add(soundUrl.getSoundUrl());
        }
        return list1;
    }
}

