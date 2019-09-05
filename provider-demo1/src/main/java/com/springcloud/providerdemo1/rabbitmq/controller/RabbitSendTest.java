package com.springcloud.providerdemo1.rabbitmq.controller;

import com.springcloud.providerdemo1.rabbitmq.service.impl.RabbitSender;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitSendTest {
    @Autowired
    private RabbitSender rabbitSender;

    @GetMapping(value="groupOrder")
    @ApiOperation("RabbitMQ分组发送消息")
    public void groupOrder(String str){
        rabbitSender.send();
    }
}
