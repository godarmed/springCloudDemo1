package com.springcloud.consumerdemo1.FeignTest.config;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.form.MultipartFormContentProcessor;
import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;
import lombok.val;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;

import static feign.form.ContentType.MULTIPART;
import static java.util.Collections.singletonMap;

@Configuration
public class FileFeignConfig {

    /*class FeignSpringFormEncoder extends FormEncoder {

        *//**
         * Constructor with the default Feign's encoder as a delegate.
         *//*
        public FeignSpringFormEncoder() {
            this(new Encoder.Default());
        }


        *//**
         * Constructor with specified delegate encoder.
         *
         * @param delegate delegate encoder, if this encoder couldn't encode object.
         *//*
        public FeignSpringFormEncoder(Encoder delegate) {
            super(delegate);

            val processor = (MultipartFormContentProcessor) getContentProcessor(MULTIPART);
            processor.addWriter(new SpringSingleMultipartFileWriter());
            processor.addWriter(new SpringManyMultipartFilesWriter());
        }


        @Override
        public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
            if (bodyType.equals(MultipartFile.class)) {
                val file = (MultipartFile) object;
                val data = singletonMap(file.getName(), object);
                super.encode(data, MAP_STRING_WILDCARD, template);
                return;
            } else if (bodyType.equals(MultipartFile[].class)) {
                val file = (MultipartFile[]) object;
                if (file != null) {
                    val data = singletonMap(file.length == 0 ? "" : file[0].getName(), object);
                    super.encode(data, MAP_STRING_WILDCARD, template);
                    return;
                }
            }
            super.encode(object, bodyType, template);
        }
    }*/

    @Bean
    @Primary
    @Scope("prototype")
    public Encoder multipartFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        //return new FeignSpringFormEncoder(new SpringEncoder(messageConverters));
        return new SpringEncoder(messageConverters);
    }
}
