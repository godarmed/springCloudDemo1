package com.springcloud.providerdemo1;

import com.springcloud.providerdemo1.async_demo.service.AsyncTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProviderDemo1ApplicationTests {

    @Autowired
    private AsyncTest task;

    @Test
    public void testAsyncWithReturn() throws Exception {
        long start = System.currentTimeMillis();

        Future<String> task1= task.doTask("One",1000);
        Future<String> task2= task.doTask("Two",1000);
        Future<String> task3= task.doTask("Three",1000);

        int i = 1;
        while(true) {
            if (task1.isDone() && task2.isDone() && task3.isDone()) {
                //三个任务都调度完成，退出循环
                break;
            }
            //log.info("第[{}]次检索任务完成情况",i++);
            Thread.sleep(100);
        }
        log.info("任务[{}]",task1.get());

        long end = System.currentTimeMillis();
        log.info("任务全部完成，总耗时:[{}]毫秒",end - start);
    }

    @Test
    public void testAsyncWithoutReturn() throws Exception {
        long start = System.currentTimeMillis();
        final CountDownLatch latch = new CountDownLatch(10);
        for (int i = 1; i <= 10; i++) {
            task.doTaskWithoutReturn("task" + i,latch,1000);
        }
        latch.await();
        long end = System.currentTimeMillis();
        log.info("任务全部完成，总耗时:[{}]毫秒",end - start);
    }
}
