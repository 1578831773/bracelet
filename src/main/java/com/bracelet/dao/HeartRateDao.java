package com.bracelet.dao;

import com.bracelet.beans.HearRate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HeartRateDao {
    String SELECT = "*";
    String TABLE = "heart_rate";
    String INSERT = "max_rate,min_rate,date,uid";

    @Select({"select ",SELECT," from ",TABLE," where uid = #{uid}"})
    List<HearRate> selectRateById(int uid);

    @Insert({"insert into ",TABLE,"( ",INSERT," ) values(#{maxRate},#{minRate},#{date},#{uid})"})
    int insertRate(HearRate hearRate);
}
