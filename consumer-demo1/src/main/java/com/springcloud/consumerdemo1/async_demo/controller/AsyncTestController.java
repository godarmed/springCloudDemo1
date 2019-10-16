package com.springcloud.consumerdemo1.async_demo.controller;

import com.springcloud.consumerdemo1.async_demo.service.AsyncTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

@RestController
@Slf4j
@RequestMapping("/AsyncTest")
public class AsyncTestController {
    @Autowired
    AsyncTest taskService;

    @GetMapping("/task")
    public String taskExecute() {
        try {
            Future<String> r1 = taskService.doTask("a", 100);
            Future<String> r2 = taskService.doTask("b", 100);
            Future<String> r3 = taskService.doTask("c", 100);
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            log.info("线程[{}]获取的自定义请求头为[{}]", Thread.currentThread().getName(), request.getHeader("TestHeader"));
            log.info("当前线程为 {}，请求方法为 {}，请求路径为：{}", Thread.currentThread().getName(), request.getMethod(), request.getRequestURL().toString());
            while (true) {
                if (r1.isDone() && r2.isDone() && r3.isDone()) {
                    log.info("execute all tasks");
                    break;
                }
                Thread.sleep(200);
            }
            log.info("线程执行结果[{},{},{}]", r1.get(), r2.get(), r3.get());
        } catch (Exception e) {
            log.error("error executing task for {}", e.getMessage());
        }

        return "ok";
    }

    @RequestMapping("/timeTask")
    public String TimeTaskExecute(Integer taskTime,HttpServletRequest httpServletRequest) throws InterruptedException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("线程[{}]获取的自定义请求头为[{}]", Thread.currentThread().getName(), request.getHeader("Host"));
        log.info("当前线程为 {}，请求方法为 {}，请求路径为：{}", Thread.currentThread().getName(), request.getMethod(), request.getRequestURL().toString());
        long start = System.currentTimeMillis();
        final CountDownLatch latch = new CountDownLatch(15);
        for (int i = 1; i <= 1; i++) {
            taskService.doTaskWithoutReturn("task" + i, latch, taskTime);
        }
        //latch.await();
        long end = System.currentTimeMillis();
        log.info("任务全部完成，总耗时:[{}]毫秒", end - start);
        return "任务全部完成，总耗时" + (end - start) + "毫秒";
    }
}
