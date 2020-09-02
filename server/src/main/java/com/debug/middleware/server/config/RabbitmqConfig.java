package com.debug.middleware.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Configuration
public class RabbitmqConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfig.class);

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    @Autowired
    private Environment env;

    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());

        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);

        return factory;
    }

    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,cachingConnectionFactory);


        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);

        factory.setConcurrentConsumers(10);
        factory.setMaxConcurrentConsumers(15);
        factory.setPrefetchCount(10);

        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {

        cachingConnectionFactory.setPublisherConfirms(true);

        cachingConnectionFactory.setPublisherReturns(true);

        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);

        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功：correlationData({}),ack({}),cause({})", correlationData, ack, cause);
            }
        });


        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

                log.info("消息丢失：exchange({})，route({}),replyCode({}),replyText({}),message({})",exchange,routingKey,replyCode,replyText,message);

            }
        });

        return rabbitTemplate;
    }


    @Bean(name = "basicQueue")
    public Queue basicQueue(){

        return new Queue(Objects.requireNonNull(env.getProperty("mq.basic.info.queue.name")),true);
    }

    @Bean(name = "objectQueue")
    public Queue objectQueue(){

        return new Queue(Objects.requireNonNull(env.getProperty("mq.object.info.queue.name")),true);
    }

    @Bean(name = "fanoutQueueOne")
    public Queue fanoutQueueOne(){

        return new Queue(Objects.requireNonNull(env.getProperty("mq.fanout.queue.one.name")),true);
    }

    @Bean(name = "fanoutQueueTwo")
    public Queue fanoutQueueTwo(){

        return new Queue(Objects.requireNonNull(env.getProperty("mq.fanout.queue.two.name")),true);
    }
    @Bean
    public DirectExchange basicExchange(){

        return new DirectExchange(env.getProperty("mq.basic.info.exchange.name"),true,false);
    }

    @Bean
    public DirectExchange objectExchange(){

        return new DirectExchange(env.getProperty("mq.object.info.exchange.name"),true,false);
    }

    @Bean
    public DirectExchange fanoutExchange(){

        return new DirectExchange(env.getProperty("mq.fanout.exchange.name"),true,false);
    }


    @Bean
    public Binding basicBinding(){

        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(env.getProperty("mq.basic.info.routing.key.name"));

    }

    @Bean
    public Binding objectBinding(){

        return BindingBuilder.bind(objectQueue()).to(objectExchange()).with(env.getProperty("mq.object.info.routing.key.name"));


    }

    @Bean
    public BindingBuilder.DirectExchangeRoutingKeyConfigurer fanoutBindingOne(){

        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());


    }

    @Bean
    public BindingBuilder.DirectExchangeRoutingKeyConfigurer fanoutBindingTwo(){

        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());


    }

}
