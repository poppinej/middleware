package com.debug.middleware.model.mapper;

import java.util.List;

import com.debug.middleware.model.entity.RedRecord;
import org.apache.ibatis.annotations.Param;


public interface RedRecordMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(RedRecord record);

    int insertSelective(RedRecord record);



    RedRecord selectByPrimaryKey(Integer id);



    int updateByPrimaryKeySelective(RedRecord record);

    int updateByPrimaryKey(RedRecord record);
}