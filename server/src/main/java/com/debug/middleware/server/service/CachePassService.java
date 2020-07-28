package com.debug.middleware.server.service;

import com.debug.middleware.model.entity.Item;
import com.debug.middleware.model.mapper.ItemMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.mysql.cj.util.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CachePassService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemMapper itemMapper;

    private static final String keyPrefix = "item:";

    public Item getItemInfo(String itemCode) throws JsonProcessingException {

        Item item = null;

        final String key = keyPrefix+itemCode;

        ValueOperations valueOperations = redisTemplate.opsForValue();

        if(redisTemplate.hasKey(key)){

            Object res = valueOperations.get(key);

            if(res != null && !Strings.isNullOrEmpty(res.toString())){

                item =objectMapper.readValue(res.toString(),Item.class);
            }

        }else {

            item = itemMapper.selectItemByCode(itemCode);

            if(item != null){
                valueOperations.set(key,objectMapper.writeValueAsString(item));
            }else {
                valueOperations.set(key,"",30L, TimeUnit.MINUTES);
            }
        }

        return item;
    }



}
