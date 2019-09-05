package com.springcloud.providerdemo1.rabbitmq.service.impl;

import com.springcloud.providerdemo1.rabbitmq.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class RabbitReceiver {
    /**
     *
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void receive(String msg){
        log.info("mqReceive = {}",msg);
    }

    @RabbitListener(queues= {"usa.news"})
    public void doChangeOne(Message<String> request) {
        //log.info("body：[{}]",body);
        //log.info("Headers：[{}]",headers);
        log.info("消息中队列名称:[{}]",request.getQueueName());
        log.info("实际队列名称:[{}]","usa.news");
        log.info("交换机名称:[{}]",request.getExchange());
        log.info("消息体:[{}]",request.getMessages());
        //log.info("消息体:[{}]",1/0);
    }

    @RabbitListener(queues= {"usa.weather"})
    public void doChangeTwo(Message<String> request) {
        log.info("消息中队列名称:[{}]",request.getQueueName());
        log.info("实际队列名称:[{}]","usa.weather");
        log.info("交换机名称:[{}]",request.getExchange());
        log.info("消息体:[{}]",request.getMessages());
    }

    @RabbitListener(queues= {"europe.news"})
    public void doChangeThree(Message<String> request) {
        log.info("消息中队列名称:[{}]",request.getQueueName());
        log.info("实际队列名称:[{}]","europe.news");
        log.info("交换机名称:[{}]",request.getExchange());
        log.info("消息体:[{}]",request.getMessages());
    }
}
