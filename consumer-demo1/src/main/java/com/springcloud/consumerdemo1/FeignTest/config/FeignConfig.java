package com.springcloud.consumerdemo1.FeignTest.config;

import com.springcloud.consumerdemo1.FeignTest.ClientFallbackFeign;
import com.springcloud.consumerdemo1.FeignTest.feignWrapper.config.DefaultErrorDecoder;
import com.springcloud.consumerdemo1.FeignTest.feignWrapper.config.DefaultMultipartFileEncoder;
import com.springcloud.consumerdemo1.FeignTest.feignWrapper.fallbacks.FeignHystrixFactory;
import com.springcloud.consumerdemo1.FeignTest.feignWrapper.fallbacks.IHystrix;
import com.springcloud.consumerdemo1.FeignTest.hystrix.ClientFallbackFeignHystrix;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.ContentType;
import feign.form.FormEncoder;
import feign.form.MultipartFormContentProcessor;
import feign.form.spring.SpringFormEncoder;
import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;
import feign.hystrix.FallbackFactory;
import lombok.val;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import static feign.form.ContentType.MULTIPART;
import static java.util.Collections.singletonMap;

@Configuration
public class FeignConfig {
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    //远程Feign调用异常解码器
    @Bean
    public ErrorDecoder errorDecoder(){
        return new DefaultErrorDecoder();
    }

    //自定义异常处理工厂类
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

    //自定义全局异常处理
}
