package com.debug.middleware.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.debug.middleware.model.entity.Item;
import org.springframework.stereotype.Component;

@Mapper
public interface ItemMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);

    Item selectItemByCode(@Param("code") String code);
}