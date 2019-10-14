package com.springcloud.consumerdemo1.rabbitmq.service;


import com.springcloud.consumerdemo1.rabbitmq.entity.Message;
import org.springframework.stereotype.Service;

@Service
public interface MqService {
    <T> void push(Message<T> var1);
}
