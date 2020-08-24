package com.debug.middleware.server;


import com.debug.middleware.server.entity.Person;
import com.debug.middleware.server.rabbitmq.publisher.BasicPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RabbitmqTest {

    private static final Logger log = LoggerFactory.getLogger(RabbitmqTest.class);

    @Autowired
    private BasicPublisher basicPublisher;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void test1(){

        String msg = "java是我的小猫咪";

        basicPublisher.sendMessage(msg);


    }

    @Test
    public void test2(){

        Person person = new Person(1,"大圣","debug");



        basicPublisher.sendObjectMsg(person);


    }


}
