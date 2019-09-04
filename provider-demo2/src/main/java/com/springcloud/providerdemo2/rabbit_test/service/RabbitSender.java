package com.springcloud.providerdemo2.rabbit_test.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RabbitSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        String msg = "mqsender send ..." + new Date();
        amqpTemplate.convertAndSend("myQueue", msg);
    }

    public void sendOrder(){
        String msg = "mqsender send ..." + new Date();
        amqpTemplate.convertAndSend("myQueue","cumputer", msg);
    }
}
