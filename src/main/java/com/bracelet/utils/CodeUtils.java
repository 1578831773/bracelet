package com.bracelet.utils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class CodeUtils{
    final static String appID = "MBVcwh6xp94EsX1ojjOCtxSA-gzGzoHsz";
    final static String  appKey = "VqUUzLguK56tBmdK8T4snC4F";
    final static String matserkey  = "DJf0dgjAsmOwtoG7vIBveRli";
    static {
        AVOSCloud.initialize(appID,appKey,matserkey);
    }

    /**
     * 产生验证码
     * @param phone 用户的手机号
     * @return 返回发送验证码是否发送成功
     */
    public static boolean getCode(String phone){
        try {
            AVOSCloud.requestSMSCode(phone, "女性防狼手环", "验证操作", 10);  // 10 分钟内有效
            return true;
        } catch (AVException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断验证码是否正确
     * @param phone 用户的手机号
     * @param code 验证码
     * @return 验证码是否正确
     */
    public static boolean checkCode(String phone,String code){
        try {
            AVOSCloud.verifySMSCode(code, phone);
            /* 验证成功 */
            return true;
        } catch (AVException ex) {
            /* 验证失败 */
            return false;
        }
    }
}