package com.debug.middleware.server;


import com.debug.middleware.server.entity.EventInfo;
import com.debug.middleware.server.rabbitmq.publisher.ModelPublisher;
import com.debug.middleware.server.testEvent.Publisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EventTest {

    @Autowired
    private Publisher publisher;

    @Autowired
    private ModelPublisher modelPublisher;


    @Test
    public void test(){

        publisher.sendMsg();
    }

    @Test
    public void test2(){

        EventInfo eventInfo = new EventInfo(1,"增删查改模块","fanoutExchange","rua");
        modelPublisher.sendMsg(eventInfo);
    }
}
