package com.springcloud.consumerdemo1.rabbitmq.service.impl;

import com.springcloud.consumerdemo1.FeignTest.ClientFallbackFeign;
import com.springcloud.consumerdemo1.async_demo.service.AsyncTest;
import com.springcloud.consumerdemo1.rabbitmq.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
@Slf4j
public class RabbitReceiver {
    @Autowired
    AsyncTest taskService;

    @Autowired
    ClientFallbackFeign clientFallbackFeign;

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

    @RabbitListener(queues= {"usa.news"},
            containerFactory = "singleListenerContainer",
            errorHandler = "rabbitListenerErrorHandler")
    public void doChangeOne(Message<String> messages) {
        //获取消息
        String authToken = messages.getMessages().get(0);
        //塞入请求
        System.setProperty("auth_token",authToken);
        //同步发送
        clientFallbackFeign.getUser();
        //异步处理
        for (int i = 1; i <= 10; i++) {
            taskService.feignTest(1000);
        }
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
