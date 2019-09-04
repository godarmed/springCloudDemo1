package com.springcloud.providerdemo2.rabbit_test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitReceive {
    /**----------- 模拟消息分组 --------------------*/
    /**
     * 数码供应商服务  接收消息
     * 消息发到交换机，交换机根据不同的key 发送到不同的队列
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
    ))
    public void receiveComputer(String msg){
        log.info(" receiveComputer service = {}" , msg );
    }
    /**
     * 水果供应商服务  接收消息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("fruitOrder"),
            key = "fruit",
            exchange = @Exchange("myOrder")
    ))
    public void receiveFruit(String msg){
        log.info(" receiveFruit service = {}" , msg );
    }
}
