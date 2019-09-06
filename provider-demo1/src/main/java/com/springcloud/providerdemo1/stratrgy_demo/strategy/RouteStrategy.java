package com.springcloud.providerdemo1.stratrgy_demo.strategy;

import org.springframework.stereotype.Service;

@Service
public interface RouteStrategy {
    public String methodOne(String str);

    public String methodTwo(String str);

    public String methodThree(String str);
}
