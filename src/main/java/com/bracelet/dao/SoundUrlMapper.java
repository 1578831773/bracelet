package com.bracelet.dao;

import com.bracelet.beans.SoundUrl;
import com.bracelet.beans.SoundUrlExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SoundUrlMapper {
    long countByExample(SoundUrlExample example);

    int deleteByExample(SoundUrlExample example);

    int insert(SoundUrl record);

    int insertSelective(SoundUrl record);

    List<SoundUrl> selectByExample(SoundUrlExample example);

    int updateByExampleSelective(@Param("record") SoundUrl record, @Param("example") SoundUrlExample example);

    int updateByExample(@Param("record") SoundUrl record, @Param("example") SoundUrlExample example);
}