package com.springcloud.providerdemo2.rabbit_test.config;

import com.springcloud.providerdemo2.rabbit_test.enums.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitExchangeConfig
{
  /*  //直连交换机
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(RabbitConstant.EXCHANGE_NAME);
    }

    //队列
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(RabbitConstant.QUEUE_NAME).build();
    }

    //绑定
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(this.queue()).to(this.defaultExchange()).with(RabbitConstant.ROUTING_KEY);
    }*/
}