package com.springcloud.providerdemo2;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRabbit
public class ProviderDemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(ProviderDemo2Application.class, args);
    }

}
