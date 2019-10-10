package com.springcloud.consumerdemo1.FeignTest.config;

import com.springcloud.consumerdemo1.FeignTest.ClientFallbackFeign;
import com.springcloud.global.feignWrapper.config.DefaultErrorDecoder;
import com.springcloud.global.feignWrapper.config.DefaultMultipartFileEncoder;
import com.springcloud.global.feignWrapper.fallbacks.FeignHystrixFactory;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class FeignConfig {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    //远程Feign调用异常解码器
    @Bean
    public ErrorDecoder errorDecoder(){
        return new DefaultErrorDecoder();
    }

    //自定义异常处理工厂类(不唯一，可以根据接口有多个)
    @Bean
    public FallbackFactory fallbackFactory(){
        return new FeignHystrixFactory(ClientFallbackFeign.class);
    }

    //文件上传编码器
    @Bean
    @Scope("prototype")
    public Encoder encoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new DefaultMultipartFileEncoder(new SpringEncoder(messageConverters));
    }
}
