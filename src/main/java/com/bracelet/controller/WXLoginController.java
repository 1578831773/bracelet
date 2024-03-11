package com.bracelet.controller;

import com.bracelet.beans.WXSessionModel;
import com.bracelet.service.MessageService;
import com.bracelet.utils.HttpClientUtil;
import com.bracelet.utils.JsonUtils;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WXLoginController
{
    @Autowired
    MessageService messageService;
    private final String APPID = "wxc97b05d9b0870590";
    private final String SECRET = "f59e25c10b045600863775cbc8774da5";
    private final String grant_type = "authorization_code";

    /**
     * 微信登录
     * @param code 代表用户的临时的code
     * @return 用户的openId
     */
    @ResponseBody
    @RequestMapping(value={"/wxLogin"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    public Map<String, String> wxLogin(String code)
    {
        System.out.println("wxlogin - code: " + code);
        Map<String, String> map = new HashMap();
        if (code == null)
        {
            map.put("openId", null);
            return map;
        }
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";

        String wxResult = HttpClientUtil.doGet(url);
        System.out.println(wxResult);
        if (wxResult.indexOf("invalid") > 0)
        {
            map.put("openId", null);
            return map;
        }
        WXSessionModel model = (WXSessionModel)JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);
        Integer integer = this.messageService.getUserId(model.getOpenid());
        if (integer == null) {
            this.messageService.insertUser(model.getOpenid());
        }
        map.put("openId", model.getOpenid());
        return map;
    }
}
