package com.springcloud.providerdemo1.async_demo.controller;

import com.springcloud.providerdemo1.async_demo.service.AsyncTest;
import com.springcloud.providerdemo1.async_demo.service.ReadWriteLock;
import com.springcloud.providerdemo1.async_demo.service.SynchronizedLock;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@Slf4j
public class ReadWriteController {
    @Autowired
    AsyncTest asyncTest;

    ThreadLocalRandom random = ThreadLocalRandom.current();

    @GetMapping("/readWriteLockTest/{testNum}")
    public void readWriteLockTest(@PathVariable("testNum") Integer testNum){
        long totalTime = 0;
        for (int j = 0; j < 20; j++) {
            //要操作的Map
            ReadWriteLock<String> readWriteLock = new ReadWriteLock();

            readWriteLock.clearMap();
            Map map = readWriteLock.getMap();
            CountDownLatch countDownLatch = new CountDownLatch(testNum);
            setOridinoaryMap(map,testNum,5);
            long start = System.currentTimeMillis();
            for (int i = 0; i < testNum; i++) {
                int temp = random.nextInt(testNum + 1);
                String key = String.valueOf(i);
                asyncTest.readWriteLockTest(readWriteLock,key,"aaa"+key,countDownLatch);
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            log.info("完成全部任务[{}]，耗时:[{}]毫秒",Thread.currentThread().getName(),(end - start));
            totalTime += end - start;
        }
        log.info("完成全部任务平均耗时:[{}]毫秒",totalTime/20);
    }

    @GetMapping("/currentHashMapTest/{testNum}")
    public void currentHashMapTest(@PathVariable("testNum") Integer testNum){
        long totalTime = 0;
        for (int j = 0; j < 20; j++) {
            Map map = new ConcurrentHashMap();
            CountDownLatch countDownLatch = new CountDownLatch(testNum);
            setOridinoaryMap(map,testNum,5);
            long start = System.currentTimeMillis();
            for (int i = 0; i < testNum; i++) {
                int temp = random.nextInt(testNum + 1);
                String key = String.valueOf(i);
                asyncTest.currentHashMapTest(map,key,"aaa"+key,countDownLatch);
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            log.info("完成全部任务[{}]，耗时:[{}]毫秒",Thread.currentThread().getName(),(end - start));
            totalTime += end - start;
        }
        log.info("完成全部任务平均耗时:[{}]毫秒",totalTime/20);
    }

    @GetMapping("/synchronizedTest/{testNum}")
    public void synchronizedTest(@PathVariable("testNum") Integer testNum){
        long totalTime = 0;
        for (int j = 0; j < 20; j++) {
            //要操作的Map
            SynchronizedLock<String> synchronizedLock = new SynchronizedLock<>();
            synchronizedLock.clearMap();
            Map map =  synchronizedLock.getMap();
            CountDownLatch countDownLatch = new CountDownLatch(testNum);
            setOridinoaryMap(map,testNum,5);
            long start = System.currentTimeMillis();
            for (int i = 0; i < testNum; i++) {
                int temp = random.nextInt(testNum + 1);
                String key = String.valueOf(i);
                asyncTest.synchronizedTest(map,key,"aaa"+key,countDownLatch);
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            totalTime += end - start;
            log.info("完成全部任务[{}]，耗时:[{}]毫秒",Thread.currentThread().getName(),(end - start));
        }
        log.info("完成全部任务平均耗时:[{}]毫秒",totalTime/20);
    }

    public static void setRandomMap(Map map,Integer length){
        Random random = new Random();
        for (int i = 0; i < length; i+=random.nextInt(5)) {
            String temp = String.valueOf(i);
            map.put(temp,temp+1);
        }
    }

    public static void setOridinoaryMap(Map map,Integer length,Integer interval){
        for (int i = 0; i < length; i+=interval) {
            String temp = String.valueOf(i);
            map.put(temp,temp+1);
        }
    }
}
