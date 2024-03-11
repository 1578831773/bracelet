package com.bracelet.dao;

import com.bracelet.beans.PicUrl;
import com.bracelet.beans.PicUrlExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PicUrlMapper {
    long countByExample(PicUrlExample example);

    int deleteByExample(PicUrlExample example);

    int insert(PicUrl record);

    int insertSelective(PicUrl record);

    List<PicUrl> selectByExample(PicUrlExample example);

    int updateByExampleSelective(@Param("record") PicUrl record, @Param("example") PicUrlExample example);

    int updateByExample(@Param("record") PicUrl record, @Param("example") PicUrlExample example);
}