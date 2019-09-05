package com.springcloud.providerdemo1.rabbitmq.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CallbackConfig {
	//交换机
	public static final String EXCHANGE_NAME = "topic.country.exchange";
	//路由表
	public static final Map<Integer, String> RESOURCE_MAP = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(2, "usa.news");
			put(3, "usa.weather");
			put(4, "europe.news");
			put(5, "europe.weather");
			put(6, "cn.news");
			put(7, "cn.weather");
		}
	};

	//连接工厂
	@Autowired
    ConnectionFactory connectionFactory;

	@Bean
	public String[] countryQueueDefined() throws IOException {
    	if(RESOURCE_MAP != null) {
			//获得信道
    		Channel channel = connectionFactory.createConnection().createChannel(false);
			//获得队列名称
    		Collection<String> names = RESOURCE_MAP.values();
    		String[] queueNames = new String[RESOURCE_MAP.size()];
    		int i = 0;
    		for (String name: names) {
				//声明队列
    			channel.queueDeclare(name, true, false, false, null);
				//声明路由名字和类型
    			channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
				//绑定路由和队列
    			channel.queueBind(name, EXCHANGE_NAME, name);
    			queueNames[i++] = name;
    		}
    		return queueNames;
    	}
		return null;
	}
}
