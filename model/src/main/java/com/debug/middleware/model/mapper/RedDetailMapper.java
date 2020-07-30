package com.debug.middleware.model.mapper;

import java.util.List;

import com.debug.middleware.model.entity.RedDetail;
import org.apache.ibatis.annotations.Param;


public interface RedDetailMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(RedDetail record);

    int insertSelective(RedDetail record);



    RedDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RedDetail record);

    int updateByPrimaryKey(RedDetail record);
}