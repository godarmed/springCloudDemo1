package com.springcloud.providerdemo1;

import com.springcloud.providerdemo1.async_demo.service.AsyncTest;
import com.springcloud.providerdemo1.rabbitmq.service.impl.RabbitSender;
import javafx.concurrent.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderDemo1ApplicationTests {

    @Autowired
    private AsyncTest task;

    @Test
    public void test() throws Exception {

        task.doTaskOne();
        task.doTaskTwo();
        task.doTaskThree();

        Thread.currentThread().join();
    }

    @Autowired
    private RabbitSender rabbitSender;

    @Test
    public void rabbitSendMes() {
        rabbitSender.send();
    }



}
