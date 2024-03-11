package com.bracelet.dao;

import com.bracelet.beans.Route;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * create table route(
 *     id int primary key auto_increment,
 *     user_id int not null,
 *     lat double not null,
 *     log double not null
 *  );
 */
@Mapper
public interface RouteDao {

    String TABLE = "route";
    String INSERT = "openid, lat, log";
    String SELECT = "openid , lat, log";

    @Insert({"insert into ", TABLE, " (", INSERT, ") values(#{openId}, #{lat}, #{log})"})
    int insert(Route route);

    @Select({"select ", SELECT, " from ", TABLE, " where openid = #{openId}"})
    ArrayList<Route> select(String openId);

}
