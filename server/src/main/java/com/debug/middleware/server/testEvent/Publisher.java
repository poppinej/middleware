package com.debug.middleware.server.testEvent;


import com.debug.middleware.server.entity.LoginEvent;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Publisher {

    private static final Logger log = LoggerFactory.getLogger(Publisher.class);


    @Autowired
    private ApplicationEventPublisher publisher;

    public void sendMsg(){

        LoginEvent loginEvent = new LoginEvent(this,"debug"
                ,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),"127.0.0.1");

        publisher.publishEvent(loginEvent);

        log.info("发送消息：{}",loginEvent);
    }
}
