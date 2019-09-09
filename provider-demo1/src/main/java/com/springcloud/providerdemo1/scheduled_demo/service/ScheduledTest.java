package com.springcloud.providerdemo1.scheduled_demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ScheduledTest {
    /*
    * 每5秒执行该方法
    * */
    @Scheduled(cron = "0/5 * * * * ?")
    public void corn() throws InterruptedException {
        log.info("当前时间:[{}]",new Date(System.currentTimeMillis()));
        Thread.currentThread().sleep(6000);
    }

    /*
     * 每1秒执行该方法
     * */
   /* @Scheduled(fixedDelay = 1000*2,initialDelay = 1000*5)
    public void cornOne() throws InterruptedException {
        log.info("当前时间:[{}]",new Date(System.currentTimeMillis()));
        Thread.currentThread().sleep(2000);
    }*/
}
