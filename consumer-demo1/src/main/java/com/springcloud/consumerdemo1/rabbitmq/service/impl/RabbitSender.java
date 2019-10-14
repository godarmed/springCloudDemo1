package com.springcloud.consumerdemo1.rabbitmq.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RabbitSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        String msg = "mqsender send ..." + new Date();
        amqpTemplate.convertAndSend("myQueue", msg);
    }
}
