package com.springcloud.providerdemo1.async_demo.service;

import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.CurrentHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

@Service
@Slf4j
public class AsyncTest {

    public static Random random = new Random();

    @Async("taskExecutor")
    public Future<String> doTask(String taskName,Integer... taskTime) throws Exception {
        log.info("开始做任务[{}]",taskName);
        long start = System.currentTimeMillis();
        Thread.sleep(taskTime[0]!=null?taskTime[0]:random.nextInt(10000));
        long end = System.currentTimeMillis();
        //上下文获取测试
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("自定义请求头为[{}]", request.getHeader("MyHeader"));
        log.info("当前线程为 {}，请求方法为 {}，请求路径为：{}", Thread.currentThread().getName(), request.getMethod(), request.getRequestURL().toString());
        //log.info("错误点[{}]",1/0);
        log.info("完成任务[{}]，耗时:[{}]毫秒",taskName,(end - start));
        return new AsyncResult<>("任务"+taskName+"完成");
    }

    @Async("taskExecutor")
    public void doTaskWithoutReturn(String taskName,CountDownLatch latch,Integer... taskTime){
        try{
            log.info("开始做任务[{}]",taskName);
            long start = System.currentTimeMillis();
            Thread.sleep(taskTime[0]!=null?taskTime[0]:random.nextInt(10000));
            long end = System.currentTimeMillis();
            //log.info("错误点[{}]",1/0);
            log.info("完成任务[{}]，耗时:[{}]毫秒",taskName,(end - start));
        }catch (Exception e){
            log.error("出现错误[{}]",e.getStackTrace());
        }finally{
            latch.countDown();
        }
    }

    //读写锁测试
    @Async("taskExecutor")
    public void readWriteLockTest(ReadWriteLock readWriteLock,String key,String value,CountDownLatch latch){
         try{
            log.info("开始做任务[{}]",Thread.currentThread().getName());
            long start = System.currentTimeMillis();

            //读取Map,如果没有该Key则写入
            if(readWriteLock.readMap(key) == null){
                readWriteLock.writeMap(key,value);
                log.info("当前线程[{}],key[{}]不存在",Thread.currentThread().getName(),key);
            }else{
                log.info("当前线程[{}],key[{}]存在",Thread.currentThread().getName(),key);
            }
            log.info("当前线程[{}],map[{}],",Thread.currentThread().getName(),readWriteLock.getMap().toString());
            long end = System.currentTimeMillis();
            log.info("完成任务[{}]，耗时:[{}]毫秒",Thread.currentThread().getName(),(end - start));
         }catch (Exception e){
            log.error("出现错误[{}]",e.getStackTrace());
         }finally{
            latch.countDown();
        }
    }

    @Async("taskExecutor")
    public void currentHashMapTest(Map map,String key,String value,CountDownLatch latch){
        try{
            log.info("开始做任务[{}]",Thread.currentThread().getName());
            long start = System.currentTimeMillis();

            //读取Map,如果没有该Key则写入
            if(!map.containsKey(key)){
                map.put(key,value);
                log.info("当前线程[{}],key[{}]不存在",Thread.currentThread().getName(),key);
            }else{
                log.info("当前线程[{}],key[{}]存在",Thread.currentThread().getName(),key);
            }
            log.info("当前线程[{}],map[{}],",Thread.currentThread().getName(),map.toString());
            long end = System.currentTimeMillis();
            log.info("完成任务[{}]，耗时:[{}]毫秒",Thread.currentThread().getName(),(end - start));
        }catch (Exception e) {
            log.error("出现错误[{}]",e.getStackTrace());
        }finally{
            latch.countDown();
        }
    }

    @Async("taskExecutor")
    public void synchronizedTest(Map map,String key,String value,CountDownLatch latch){
        try{
            log.info("开始做任务[{}]",Thread.currentThread().getName());
            long start = System.currentTimeMillis();

            //读取Map,如果没有该Key则写入
            if(!map.containsKey(key)){
                map.put(key,value);
                log.info("当前线程[{}],key[{}]不存在",Thread.currentThread().getName(),key);
            }else{
                log.info("当前线程[{}],key[{}]存在",Thread.currentThread().getName(),key);
            }
            log.info("当前线程[{}],map[{}],",Thread.currentThread().getName(),map.toString());
            long end = System.currentTimeMillis();
            log.info("完成任务[{}]，耗时:[{}]毫秒",Thread.currentThread().getName(),(end - start));
        }finally{
            latch.countDown();
        }
    }
}
