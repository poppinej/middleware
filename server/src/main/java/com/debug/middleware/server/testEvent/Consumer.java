package com.debug.middleware.server.testEvent;

import com.debug.middleware.server.entity.LoginEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class Consumer implements ApplicationListener<LoginEvent> {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    @Override
    public void onApplicationEvent(LoginEvent loginEvent) {




        log.info("接收到信息：{}",loginEvent);

    }
}
