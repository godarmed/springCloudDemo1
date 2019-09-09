package com.springcloud.providerdemo1.async_demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

@Service
@Slf4j
public class AsyncTest {

    public static Random random = new Random();

    @Async("taskExecutor")
    public Future<String> doTask(String taskName,Integer... taskTime) throws Exception {
        log.info("开始做任务[{}]",taskName);
        long start = System.currentTimeMillis();
        Thread.sleep(taskTime[0]!=null?taskTime[0]:random.nextInt(10000));
        long end = System.currentTimeMillis();
        //log.info("错误点[{}]",1/0);
        log.info("完成任务[{}]，耗时:[{}]毫秒",taskName,(end - start));
        return new AsyncResult<>("任务"+taskName+"完成");
    }

    @Async("taskExecutor")
    public void doTaskWithoutReturn(String taskName,CountDownLatch latch,Integer... taskTime){
        try{
            log.info("开始做任务[{}]",taskName);
            long start = System.currentTimeMillis();
            Thread.sleep(taskTime[0]!=null?taskTime[0]:random.nextInt(10000));
            long end = System.currentTimeMillis();
            //log.info("错误点[{}]",1/0);
            log.info("完成任务[{}]，耗时:[{}]毫秒",taskName,(end - start));
        }catch (Exception e){
            log.error("出现错误[{}]",e.getStackTrace());
        }finally{
            latch.countDown();
        }
    }
}
