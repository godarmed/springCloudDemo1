package com.springcloud.consumerdemo1.FeignTest.feignWrapper.fallbacks;

public interface IHystrix {
    Throwable setThrowable(Throwable throwable);
}
