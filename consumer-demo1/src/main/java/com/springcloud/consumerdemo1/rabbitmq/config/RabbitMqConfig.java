package com.springcloud.consumerdemo1.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${spring.rabbitmq.host}")
    private String rabbitmq_host;

    @Value("${spring.rabbitmq.port}")
    private Integer rabbitmq_port;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmq_username;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmq_password;

  /*  @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(rabbitmq_host);
        factory.setPort(rabbitmq_port);
        factory.setUsername(rabbitmq_username);
        factory.setPassword(rabbitmq_password);
        return factory;
    }*/

   /* @Bean(name = "singleListenerContiainer")
    public SimpleRabbitListenerContainerFactory listenerContainerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        // setConnectionFactory：设置spring-amqp的ConnectionFactory。
        factory.setConnectionFactory(connectionFactory());
        //消息序列化类型
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //setConcurrentConsumers：设置每个MessageListenerContainer将会创建的Consumer的最小数量，默认是1个。
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        //setPrefetchCount：设置每次请求发送给每个Consumer的消息数量。
        factory.setPrefetchCount(1);
        //是否设置Channel的事务。
        factory.setChannelTransacted(false);
        //setTxSize：设置事务当中可以处理的消息数量。
        factory.setTxSize(1);
        //设置当rabbitmq收到nack/reject确认信息时的处理方式，设为true，扔回queue头部，设为false，丢弃。
        factory.setDefaultRequeueRejected(true);
        //setErrorHandler：实现ErrorHandler接口设置进去，所有未catch的异常都会由ErrorHandler处理。
       *//* factory.setErrorHandler(new ErrorHandler() {
            @Override
            public void handleError(Throwable throwable) {
                System.out.println("------------------------->丢弃消息啦"+throwable);
            }
        });*//*
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }*/
}
