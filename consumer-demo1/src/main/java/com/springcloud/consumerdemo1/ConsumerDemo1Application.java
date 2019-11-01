package com.springcloud.consumerdemo1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.springcloud.consumerdemo1", "com.springcloud.global"})
@EnableDiscoveryClient
@EnableFeignClients
//@EnableRabbit
@EnableAsync
@EnableScheduling
@MapperScan("com.springcloud.consumerdemo1.mybatis_demo.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class ConsumerDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerDemo1Application.class, args);
    }

    /**
     * @Description:
     * @Param:
     * @return:
     * @Author:
     * @Date: 2018/6/15
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
