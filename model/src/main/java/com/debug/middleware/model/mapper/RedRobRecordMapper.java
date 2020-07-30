package com.debug.middleware.model.mapper;

import java.util.List;

import com.debug.middleware.model.entity.RedRobRecord;
import org.apache.ibatis.annotations.Param;


public interface RedRobRecordMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(RedRobRecord record);

    int insertSelective(RedRobRecord record);


    RedRobRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RedRobRecord record);

    int updateByPrimaryKey(RedRobRecord record);
}