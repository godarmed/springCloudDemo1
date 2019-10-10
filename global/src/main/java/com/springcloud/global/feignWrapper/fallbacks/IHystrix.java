package com.springcloud.global.feignWrapper.fallbacks;

public interface IHystrix {
    Throwable setThrowable(Throwable throwable);
}
