package com.debug.middleware.server.rabbitmq.publisher;

import com.debug.middleware.server.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component

public class BasicPublisher {

    private static final Logger log = LoggerFactory.getLogger(BasicPublisher.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment env;

    public void sendMessage(String message){

        if(!Strings.isNullOrEmpty(message)){

            try{

                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.basic.info.exchange.name"));

                rabbitTemplate.setRoutingKey(env.getProperty("mq.basic.info.routing.key.name"));

                Message msg = MessageBuilder.withBody(message.getBytes("utf-8")).build();

                rabbitTemplate.convertAndSend(msg);

                log.info("发送消息：{}",msg);


            }catch (Exception e){

                log.error("发送消息异常：{}",e.fillInStackTrace());

            }
        }
    }

    public void sendObjectMsg(Person p) {

        if (p != null) {

            try {


                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.object.info.exchange.name"));

                rabbitTemplate.setRoutingKey(env.getProperty("mq.object.info.routing.key.name"));

                rabbitTemplate.convertAndSend(p, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {

                        MessageProperties messageProperties = message.getMessageProperties();

                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Person.class);

                        return message;
                    }
                });

                log.info("发送消息对象：{}",p);

            } catch (Exception e) {

                log.error("发送对象消息失败！：{}",e.fillInStackTrace());

            }


        }

    }}
