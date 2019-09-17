package com.springcloud.providerdemo1.async_demo.service;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SynchronizedLock <T> {
    private final Map<String,T> map = new HashMap<>();

    //获取map
    public Map<String, T> getMap() {
        return map;
    }

    //读取map
    public synchronized T readMap(String key){
        log.info("Thread [{}] be ready to read map",Thread.currentThread().getName());
        T obj = null;
        //Thread.sleep((long)(Math.random()*1000));
        obj = map.get(key);
        log.info("Thread [{}] have read map",Thread.currentThread().getName());
        return obj;
    }
    //写入map
    public synchronized void writeMap(String key,T value){
        log.info("Thread [{}] be ready to write map",Thread.currentThread().getName());
        //Thread.sleep((long)(Math.random()*1000));
        if(!map.containsKey(key)) {
            map.put(key, value);
        }else{
            //throw new Exception("Key is existed");
        }
        log.info("Thread [{}] have write map",Thread.currentThread().getName());
    }
    //清空map
    public synchronized void clearMap(){
        log.info("Thread [{}] be ready to clear map",Thread.currentThread().getName());
        map.clear();
        log.info("Thread [{}] have clear map",Thread.currentThread().getName());;
    }
}
