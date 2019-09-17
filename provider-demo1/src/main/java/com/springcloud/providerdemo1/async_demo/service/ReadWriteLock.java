package com.springcloud.providerdemo1.async_demo.service;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class ReadWriteLock<T>{
    private final Map<String,T> map = new HashMap<>();

    private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

    //获取map
    public Map<String, T> getMap() {
        return map;
    }

    //读取map
    public T readMap(String key){
        rw.readLock().lock();
        log.info("Thread [{}] be ready to read map",Thread.currentThread().getName());
        T obj = null;
        try{
             //Thread.sleep((long)(Math.random()*1000));
             obj = map.get(key);
        }finally{
            log.info("Thread [{}] have read map",Thread.currentThread().getName());
            rw.readLock().unlock();
        }
        return obj;
    }
    //写入map(不覆盖)
    public void writeMap(String key,T value) throws Exception {
        rw.writeLock().lock();
        log.info("Thread [{}] be ready to write map",Thread.currentThread().getName());
        try{
            //Thread.sleep((long)(Math.random()*1000));
            if(!map.containsKey(key)){
                map.put(key,value);
            }else{
                //throw new Exception("Key is existed");
            }
        }finally{
            log.info("Thread [{}] have write map",Thread.currentThread().getName());
            rw.writeLock().unlock();
        }
    }
    //清空map
    public void clearMap(){
        rw.writeLock().lock();
        log.info("Thread [{}] be ready to clear map",Thread.currentThread().getName());
        try{
            map.clear();
        }finally{
            log.info("Thread [{}] have clear map",Thread.currentThread().getName());
            rw.writeLock().unlock();
        }
    }
}
