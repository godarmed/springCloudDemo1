package com.springcloud.providerdemo2;

import com.springcloud.providerdemo2.rabbit_test.service.RabbitReceive;
import com.springcloud.providerdemo2.rabbit_test.service.RabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderDemo2ApplicationTests {

    @Autowired
    private RabbitSender rabbitSender;

    @Test
    public void senderOrderTest() {
        rabbitSender.sendOrder();
    }

}
