package com.springcloud.providerdemo1.strategy;

import org.springframework.stereotype.Service;

@Service
public interface RouteStrategy {
    public String methodOne(String str);

    public String methodTwo(String str);

    public String methodThree(String str);
}
