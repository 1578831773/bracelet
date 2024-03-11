package com.bracelet.service;

import com.bracelet.beans.BraceletUser;
import com.bracelet.dao.BraceletUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BraceletUserService {
    @Autowired
    BraceletUserDao braceletUserDao;

    public boolean isExist(String phone,String code){
        if(code == null || phone == null)return false;
        String cd = braceletUserDao.getCode(phone);
        if(code.equals(cd))return true;
        return false;
    }
    public Integer getUid(String phone){
        return braceletUserDao.selectUser(phone);
    }
}
