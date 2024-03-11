package com.bracelet.service;

import com.bracelet.beans.*;
import com.bracelet.beans.UserExample.Criteria;
import com.bracelet.dao.UserInfoMapper;
import com.bracelet.dao.UserMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService
{

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserMapper userMapper;

    /**
     * 更新指定用户的数据信息
     * * @param userInfo 需要更新后的用户信息
     * @return 返回表更新是否成功
     */
    public boolean updataUserInfo(UserInfo userInfo)
    {
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        criteria.andUseridEqualTo(userInfo.getUserid());
        userInfo.setUserid(null);
        int i = this.userInfoMapper.updateByExampleSelective(userInfo, userInfoExample);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 通过id查找用户的信息
     * @param id 用户的id
     * @return 返回对应id的全部用户信息
     */
    public UserInfo selectUserInfo(Integer id)
    {
        if (id == null) {
            return null;
        }
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        criteria.andUseridEqualTo(id);
        List<UserInfo> list = this.userInfoMapper.selectByExample(userInfoExample);
        if (list.size() > 0) {
            return (UserInfo)list.get(0);
        }
        return null;
    }

    /**
     * 通过openid来查找对应的id号
     * @param openid 用户的id号
     * @return 返回传入openid的id号
     */
    public Integer getUserId(String openid)
    {
        if (openid == null) {
            return null;
        }
        UserExample userExample = new UserExample();
        Criteria criteria = userExample.createCriteria();
        criteria.andOpenidEqualTo(openid);
        List<User> list = this.userMapper.selectByExample(userExample);
        if (list.size() > 0) {
            return ((User)list.get(0)).getUserid();
        }
        return null;
    }

    /**
     * 往数据库中添加一个新用户
     * @param openid 用户的openid
     * @return 返回添加用户是否成功
     */
    public boolean insertUser(String openid)
    {
        if (openid == null) {
            return false;
        }
        User user = new User();
        user.setOpenid(openid);
        int i = this.userMapper.insertSelective(user);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * 通过用户的电话号码来查找他的openid
     * @param tel 用户的openid
     * @return 用户的openid
     */
    public String getopendidByTel(String tel){
        if (tel == null)return null;
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        criteria.andTelEqualTo(tel);
        List<UserInfo> list =userInfoMapper.selectByExample(userInfoExample);
        if(list.size() == 0)return null;
        else {
            UserExample userExample = new UserExample();
            Criteria criteria1 = userExample.createCriteria();
            criteria1.andUseridEqualTo(list.get(0).getUserid());
            List<User> list1 = userMapper.selectByExample(userExample);
            if(list1.size() == 0)return null;
            else return list1.get(0).getOpenid();
        }
    }
//    public boolean setUserInfo(String openid,String name,String imgUrl){
//        if(openid == null || name == null || imgUrl == null){
//            return false;
//        }
//        Integer userId = getUserId(openid);
//        if(userId == null){
//           return false;
//        }
//        UserInfoExample userInfoExample = new UserInfoExample();
//        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
//        criteria.andUseridEqualTo(userId);
//        UserInfo userInfo = new UserInfo();
//        userInfo.setImgurl(imgUrl);
//        userInfo.setUsername(name);
//        int i = this.userInfoMapper.updateByExampleSelective(userInfo, userInfoExample);
//        return true;
//    }

    /**
     * 通过用户的openid来查找用户的信息
     * @param openid 用户的openid
     * @return 返回特别属性组成的map
     */
    public Map<String, Object> getUserInfo(String openid)
    {
        if (openid == null) {
            return null;
        }
        Integer integer = getUserId(openid);
        if (integer == null) {
            return null;
        }
        UserInfo userInfo = selectUserInfo(integer);
        if (userInfo == null) {
            return null;
        }
        Map<String, Object> map = new HashMap();
       // map.put("openid", openid);
        map.put("phone", userInfo.getTel());
        return map;
    }

}

