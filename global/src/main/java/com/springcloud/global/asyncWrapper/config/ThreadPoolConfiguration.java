package com.springcloud.global.asyncWrapper.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class ThreadPoolConfiguration implements AsyncConfigurer {
    @Value("${async.max-pool-size:10}")
    int maxPoolSize;

    @Value("${async.core-pool-size:5}")
    int corePoolSize;

    @Value("${async.queue-capacity:200}")
    int queueCapacity;

    @Value("${async.keepalive-seconds:60}")
    int keepAliveSeconds;

    @Value("${async.allow-core-thread-timeout:true}")
    boolean allowCoreThreadTimeOut;

    @Value("${async.thread-name-prefix:线程池L-}")
    String threadNamePrefix;

    @Value("${async.rejected-execution-handler:CALLERRUNSPOLICY}")
    String rejectedExecutionHandler;

    //线程池配置
    @Bean("taskExecutor")
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //最大线程数：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        taskExecutor.setMaxPoolSize(maxPoolSize);
        //核心线程数：线程池创建时候初始化的线程数
        taskExecutor.setCorePoolSize(corePoolSize);
        //缓冲队列200：用来缓冲执行任务的队列
        taskExecutor.setQueueCapacity(queueCapacity);
        //允许线程的空闲时间(单位：秒)：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        //是否允许核心线程超时
        taskExecutor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        taskExecutor.setThreadNamePrefix(threadNamePrefix);
        //设置队列拒绝策略
        RejectedExecutionHandler rejectedStragety;
        rejectedExecutionHandler = rejectedExecutionHandler.toUpperCase();
        switch (rejectedExecutionHandler) {
            case "CALLERRUNSPOLICY":
                rejectedStragety = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
            case "ABORTPOLICY":
                rejectedStragety = new ThreadPoolExecutor.AbortPolicy();
                break;
            case "DISCARDOLDESTPOLICY":
                rejectedStragety = new ThreadPoolExecutor.DiscardOldestPolicy();
                break;
            case "DISCARDPOLICY":
                rejectedStragety = new ThreadPoolExecutor.DiscardPolicy();
                break;
            default:
                rejectedStragety = new ThreadPoolExecutor.CallerRunsPolicy();
                break;
        }
        taskExecutor.setRejectedExecutionHandler(rejectedStragety);
        //runnable服务包装器设置
        taskExecutor.setTaskDecorator(new ContextDecorator());
        //初始化线程池配置
        taskExecutor.initialize();
        return taskExecutor;
    }


    //异常处理配置（对于无返回值的方法）
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SpringAsyncExceptionHandler();
    }

    //自定义异常处理类
    class SpringAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
            log.error("Exception occurs in async method", throwable.getMessage());
        }
    }

    //线程上下文传递配置(仅在web环境下有效)
    class ContextDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable runnable) {
            RequestAttributes context = RequestContextHolder.currentRequestAttributes();
            //取出请求头
            return () -> {
                try {
                    //执行异步任务前添加上下文
                    RequestContextHolder.setRequestAttributes(context, true);
                    //执行异步任务
                    runnable.run();
                } finally {
                    //执行异步任务后重置上下文
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        }
    }
}
