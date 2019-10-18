package com.springcloud.consumerdemo1.async_demo.service;

import com.springcloud.consumerdemo1.annotation_demo.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AopTestService {

    @Log
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
}
