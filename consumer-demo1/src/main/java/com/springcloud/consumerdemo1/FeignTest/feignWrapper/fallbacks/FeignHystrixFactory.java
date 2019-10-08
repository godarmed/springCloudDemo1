package com.springcloud.consumerdemo1.FeignTest.feignWrapper.fallbacks;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Slf4j
public class FeignHystrixFactory<T> implements FallbackFactory<T> {
    public static final String hyPrefix = "hystrix";
    private Class<? extends T> type = null;
    private Class<T> current = null;

    public FeignHystrixFactory(Class<? extends T> type, Class<T> current) {
        this.type = type;
        this.current = current;
    }

    public FeignHystrixFactory(Class<T> current) {
        this.current = current;
    }

    @Override
    public T create(Throwable throwable) {
        Class<? extends T> clazz = null;
        if(this.type != null){
            clazz = this.type;
        }else{
            String packageName = this.current.getPackage().getName() + "." + hyPrefix;
            String className = packageName + "." + this.current.getSimpleName() + "Hystrix";
            try{
                clazz = (Class<? extends T>) Class.forName(className);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
                return null;
            }
        }

        try {
            return this.initByClass(clazz, throwable);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    private T initByClass(Class<? extends T> clazz, Throwable cause) throws RuntimeException {
        try {
            Constructor cons = clazz.getConstructor();
            T instance = (T) cons.newInstance();
            if (instance instanceof IHystrix) {
                ((IHystrix)instance).setThrowable(cause);
            }
            return instance;
        } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}
