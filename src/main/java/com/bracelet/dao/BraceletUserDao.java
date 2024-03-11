package com.bracelet.dao;

import com.bracelet.beans.BraceletUser;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BraceletUserDao {
    String TABLE = "bracelet_user";
    String INSERT = "phone,code";
    String SELECT = "uid";
    String PHONE = "phone";
    String CODE = "code";
    String ALL = "*";
    @Insert({"insert into ",TABLE,"( ",INSERT," ) values(#{phone},#{#code})"})
    public void insertUser(String phone,String code);

    @Select({"select ",SELECT," from ",TABLE," where phone = #{phone}"})
    public Integer selectUser(String phone);

    @Select({"select ",ALL," from ",TABLE," where uid  = #{uid}"})
    public BraceletUser selectUserById(int uid);

    @Select({"select ",CODE," from ",TABLE," where phone =#{phone}"})
    public String getCode(String phone);
}