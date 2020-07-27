package com.debug.middleware.server;


import com.debug.middleware.server.entity.Book;
import com.debug.middleware.server.entity.PhoneUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {


    private static final Logger log = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void one() throws InterruptedException {

        log.info("------开始redis实战----------");

        final String content = "Redis实战";
        final String key = "gk";

        ValueOperations valueOperations = redisTemplate.opsForValue();

        log.info("写入Redis");


        valueOperations.set(key, content,10L, TimeUnit.SECONDS);

        Thread.sleep(5000);

        Boolean exist = redisTemplate.hasKey(key);
        log.info("5秒还在吗：{}",exist);

        Thread.sleep(5000);

         exist = redisTemplate.hasKey(key);
        log.info("10秒还在吗：{}",exist);

    }

   @Test
    public void two() throws JsonProcessingException {

        log.info("-----------开始实战2-----------");
        Book book = new Book(1, "白给");

        ValueOperations valueOperations = redisTemplate.opsForValue();

        final String key = "存入的书";

        final String content = objectMapper.writeValueAsString(book);

        valueOperations.set(key, content);

        log.info("存入:{}", book);

        Object res = valueOperations.get(key);

        if (res != null) {

            Book result = objectMapper.readValue(res.toString(), Book.class);
            log.info("读取结果：{}", result);
        }


    }
    @Test
    public void three(){

        List<Book> books = new ArrayList<>();

        books.add(new Book(1,"WDNMD"));
        books.add(new Book(2,"A1高闪"));
        books.add(new Book(3,"汤加可乐"));
        books.add(new Book(4,"gk"));

        String key = "茄子";

        ListOperations listOperations = redisTemplate.opsForList();

        for(Book b : books){

            listOperations.leftPush(key,b);

        }

        log.info("--获取---");
        Object res = listOperations.rightPop(key);
        Book resB;

        while (res != null){

            resB = (Book)res;
            log.info("当前数据：{}",resB);
            res = listOperations.rightPop(key);
        }

    }
    @Test
    public void setTest(){

        List<PhoneUser> list = new ArrayList<>();
        list.add(new PhoneUser("110",11.0));
        list.add(new PhoneUser("120",12.0));
        list.add(new PhoneUser("130",13.0));
        list.add(new PhoneUser("140",14.0));

        final String key = "wd";

        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        for(PhoneUser u : list){

            zSetOperations.add(key,u,u.getFare());
        }

        Long size = zSetOperations.size(key);

        Set<PhoneUser> resSet = zSetOperations.reverseRange(key,0L,size);

        for(PhoneUser phoneUser : resSet){

            log.info("从缓存中取出：{}",phoneUser);
        }


    }

    public void hashTest(){


    }


}
