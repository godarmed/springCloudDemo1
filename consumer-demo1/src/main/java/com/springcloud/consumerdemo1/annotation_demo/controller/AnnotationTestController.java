package com.springcloud.consumerdemo1.annotation_demo.controller;

import com.springcloud.consumerdemo1.annotation_demo.annotation.Log;
import com.springcloud.consumerdemo1.async_demo.service.AsyncTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

@Slf4j
@RestController
public class AnnotationTestController {
    @Autowired
    AsyncTest asyncTest;

    @RequestMapping("/myLogTest")
    //对应的自定义注解，当方法上写这个注解时，就会进入切面类中
    public String sayHello(boolean isThrowable) {
        log.info("HelloController sayHello:{}","hello world!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(isThrowable){
            throw new RuntimeException("自定义异常");
        }
        return "hello";
    }

    @RequestMapping("/myLogTestAsync")
    @Log(title="日志测试模块",action="测试日志记录")
    //对应的自定义注解，当方法上写这个注解时，就会进入切面类中
    public String sayHelloAsync(boolean isThrowable) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i <10 ; i++) {
            asyncTest.aopTest(latch);
        }
        //latch.await();
        return "hello";
    }
}
