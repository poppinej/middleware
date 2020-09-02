package com.debug.middleware.server.rabbitmq.consumer;


import com.debug.middleware.server.entity.EventInfo;
import com.debug.middleware.server.rabbitmq.publisher.ModelPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Lazy(false)
public class ModelConsumer {

    private static final Logger log = LoggerFactory.getLogger(ModelConsumer.class);


    @Autowired
    public ObjectMapper objectMapper;

    @RabbitListener(queues = "${mq.fanout.queue.one.name}")
    public void consumerFanoutMsgOne(@Payload byte[] msg){

        try{
            EventInfo info = objectMapper.readValue(msg,EventInfo.class);

            log.info("fanoutExchange-one获取：{}",info);
        }catch (Exception e){
            log.error("one消息获取失败");
        }
    }


    @RabbitListener(queues = "${mq.fanout.queue.two.name}")
    public void consumerFanoutMsgTwo(@Payload byte[] msg){

        try{
            EventInfo info = objectMapper.readValue(msg,EventInfo.class);

            log.info("fanoutExchange-two获取：{}",info);
        }catch (Exception e){
            log.error("two消息获取失败");
        }
    }

}
