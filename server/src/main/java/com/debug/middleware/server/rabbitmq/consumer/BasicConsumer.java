package com.debug.middleware.server.rabbitmq.consumer;

import com.debug.middleware.server.entity.Person;
import com.debug.middleware.server.rabbitmq.publisher.BasicPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Lazy(false)
public class BasicConsumer {

    private static final Logger log = LoggerFactory.getLogger(BasicConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${mq.basic.info.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeMsg(@Payload byte[] msg){

        try{

            String message = new String(msg,"utf-8");

            log.info("接收到消息：{}",message);

        }catch (Exception e){

            log.error("接受消息发送错误：{}",e.fillInStackTrace());

        }
    }



    @RabbitListener(queues = "${mq.object.info.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeObjectMsg(@Payload Person person ){

        try{


            log.info("接收到消息：{}",person);

        }catch (Exception e){

            log.error("接受消息发送错误：{}",e.fillInStackTrace());

        }
    }

}
