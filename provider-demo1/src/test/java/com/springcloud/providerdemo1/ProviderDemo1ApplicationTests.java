package com.springcloud.providerdemo1;

import com.springcloud.providerdemo1.async_demo.service.AsyncTest;
import com.springcloud.providerdemo1.rabbitmq.service.impl.RabbitSender;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProviderDemo1ApplicationTests {

    @Autowired
    private AsyncTest task;

    @Test
    public void test() throws Exception {
        long start = System.currentTimeMillis();

        Future<String> task1= task.doTaskOne();
        Future<String> task2= task.doTaskTwo();
        Future<String> task3= task.doTaskThree();

        int i = 1;
        while(true) {
            if (task1.isDone() && task2.isDone() && task3.isDone()) {
                //三个任务都调度完成，退出循环
                break;
            }
            log.info("第[{}]次检索任务完成情况",i++);
            Thread.sleep(1000);
        }
        //等待子线程结束
        //Thread.currentThread().join();
        long end = System.currentTimeMillis();
        log.info("任务全部完成，总耗时:[{}]毫秒",end - start);
    }

}
