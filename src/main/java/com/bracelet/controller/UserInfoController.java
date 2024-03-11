package com.bracelet.controller;

import com.bracelet.beans.BraceletUser;
import com.bracelet.beans.Msg;
import com.bracelet.beans.Openid;
import com.bracelet.beans.UserInfo;
import com.bracelet.service.BraceletUserService;
import com.bracelet.service.MessageService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bracelet.utils.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController
{
    @Autowired
    MessageService messageService;

    @Autowired
    BraceletUserService braceletUserService;
    /**
     * 发送短信验证
     * @param phone 用户的手机号
     * @return 返回时候发送成功
     */
  /*  @PostMapping("/getCode")
    public Msg getCode(String phone){
        Msg msg = new Msg();
        msg.setCode(2);
        if(CodeUtils.getCode(phone)){
            msg.setCode(1);
            return msg;
        }else {
            return msg;
        }
    }*/

    /**
     * 验证短信验证码是否正确
     * @param phone 手机号
     * @param code 验证码
     * @return 返回验证码是否正确
     */
    @PostMapping("/checkCode")
    public Msg checkCode(String phone,String code){
        Msg msg = new Msg();
        msg.setCode(2);
        boolean flag = braceletUserService.isExist(phone,code);
        if(flag){
            msg.setCode(1);
            return msg;
        }else {
            return msg;
        }
    }

    /**
     * 给指定用户设置电话号码
     * @param openId 用户的openId
     * @param phone 用户的电话号码
     * @return
     */
    @PostMapping("/setTel")
    public Msg setTel(String openId,String phone,String code){
        Msg msg = new Msg();
        msg.setCode(2);
        System.out.println(code);
        System.out.println(phone);
        System.out.println(openId);
        System.out.println(braceletUserService.isExist(phone,code));
        if(openId == null || phone == null || !braceletUserService.isExist(phone,code))return msg;
        Integer integer = this.messageService.getUserId(openId);
        System.out.println(integer);
        if(integer == null)return msg;
        UserInfo userInfo = new UserInfo();
        userInfo.setTel(phone);
        userInfo.setUserid(integer);
        messageService.updataUserInfo(userInfo);
        msg.setCode(1);
        return msg;
    }

    /**
     * 返回指定用户的电话号码
     * @param openId 用户的openId
     * @return 返回用户的电话号码
     */
    @PostMapping("/getTel")
    public Map<String,String> getTel(String openId){
        Map<String,String> map = new HashMap<>();
        map.put("code","2");
        map.put("tel",null);
        if(openId == null){
            return map;
        }
        Integer integer = this.messageService.getUserId(openId);
        if(integer == null)return map;
        UserInfo userInfo = messageService.selectUserInfo(integer);
        if(userInfo == null){
            return map;
        }
        map.put("code","1");
        map.put("tel",userInfo.getTel());
        return map;
    }





    /**
     * 设置用户的个人信息
     * @param openId 用户的openId
     * @param name 用户名
     * @param imgUrl 头像的url
     * @return 返回是否设置成功
     */
    @PostMapping({"/setUserInfo"})
    public Msg setUserInfo(String openId,String name,String imgUrl)
    {
        System.out.println(openId + name + imgUrl);
        Msg msg = new Msg();
        msg.setCode(2);
        UserInfo userInfo = new UserInfo();
        if(openId == null)
            return msg;
        Integer userId  = messageService.getUserId(openId);
        if(userId == null){
            return msg;
        }
        userInfo.setUserid(userId);
        boolean isSuccess = messageService.updataUserInfo(userInfo);
        if(isSuccess){
            msg.setCode(1);
        }
        return msg;
    }

    /**
     * 返回指定用户个人信息
     * @param openId 用户的openId
     * @return 用户个人信息
     */
    @PostMapping({"/getUserInfo"})
    public Map<String, Object> getAllinfo(String openId)
    {
        Map<String, Object> map = this.messageService.getUserInfo(openId);
        if(map == null) {
            map = new HashMap<>();
            map.put("phone", null);
            return map;
        }
        return map;
    }


}
