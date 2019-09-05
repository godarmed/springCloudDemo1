package com.springcloud.providerdemo1.rabbitmq.service;


import com.springcloud.providerdemo1.rabbitmq.entity.Message;
import org.springframework.stereotype.Service;

@Service
public interface MqService {
    <T> void push(Message<T> var1);
}
