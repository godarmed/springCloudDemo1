package com.springcloud.providerdemo1.rabbitmq.service.impl;

import com.springcloud.providerdemo1.rabbitmq.entity.Message;
import com.springcloud.providerdemo1.rabbitmq.service.MqService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MqServiceImpl implements MqService{
    @Autowired
    RabbitTemplate rabbitTemplate;

    public MqServiceImpl() {
    }

    @Override
    public <T> void push(Message<T> message) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        this.rabbitTemplate.convertAndSend(message.getExchange(), message.getQueueName(), message, correlationId);
    }
}
