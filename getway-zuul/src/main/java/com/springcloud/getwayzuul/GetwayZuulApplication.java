package com.springcloud.getwayzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class GetwayZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetwayZuulApplication.class, args);
	}

}
