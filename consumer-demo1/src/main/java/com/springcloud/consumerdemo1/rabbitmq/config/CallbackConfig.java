package com.springcloud.consumerdemo1.rabbitmq.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.util.ErrorHandler;

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

	@Value("${spring.rabbitmq.host}")
	private String rabbitmq_host;

	@Value("${spring.rabbitmq.port}")
	private Integer rabbitmq_port;

	@Value("${spring.rabbitmq.username}")
	private String rabbitmq_username;

	@Value("${spring.rabbitmq.password}")
	private String rabbitmq_password;

	@Bean
	public ConnectionFactory connectionFactory(){
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost(rabbitmq_host);
		factory.setPort(rabbitmq_port);
		factory.setUsername(rabbitmq_username);
		factory.setPassword(rabbitmq_password);
		return factory;
	}

	@Bean(name = "singleListenerContainer")
	public SimpleRabbitListenerContainerFactory listenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        setConnectionFactory：设置spring-amqp的ConnectionFactory。
		factory.setConnectionFactory(connectionFactory());
		//消息序列化类型
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		//setConcurrentConsumers：设置每个MessageListenerContainer将会创建的Consumer的最小数量，默认是1个。
		factory.setConcurrentConsumers(1);
		factory.setMaxConcurrentConsumers(1);
		//setPrefetchCount：设置每次请求发送给每个Consumer的消息数量。
		factory.setPrefetchCount(1);
		//是否设置Channel的事务。
		factory.setChannelTransacted(false);
		//setTxSize：设置事务当中可以处理的消息数量。
		factory.setTxSize(1);
		//设置当rabbitmq收到nack/reject确认信息时的处理方式，设为true，扔回queue头部，设为false，丢弃。
		factory.setDefaultRequeueRejected(true);
		//setErrorHandler：实现ErrorHandler接口设置进去，所有未catch的异常都会由ErrorHandler处理。
		factory.setErrorHandler(new ErrorHandler() {
			@Override
			public void handleError(Throwable throwable) {
				System.out.println("------------------------->丢弃消息啦"+throwable);
			}
		});
		factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
		return factory;
	}


	@Bean
	public RabbitListenerErrorHandler rabbitListenerErrorHandler(){
		return new RabbitListenerErrorHandler() {
			@Override
			public Object handleError(org.springframework.amqp.core.Message message, Message<?> message1, ListenerExecutionFailedException e) throws Exception {
				System.out.println("------------"+message);
				throw new AmqpRejectAndDontRequeueException(e.getMessage());
			}
		};
	}

	@Bean
	public String[] countryQueueDefined() throws IOException {
    	if(RESOURCE_MAP != null) {
			//获得信道
    		Channel channel = connectionFactory().createConnection().createChannel(false);
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
