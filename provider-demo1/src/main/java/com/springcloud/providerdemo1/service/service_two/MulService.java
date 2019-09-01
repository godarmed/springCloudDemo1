package com.springcloud.providerdemo1.service.service_two;

import com.springcloud.providerdemo1.strategy.RouteStrategy;
import org.springframework.stereotype.Service;

@Service
public class MulService implements RouteStrategy {
    @Override
    public String methodOne(String str) {
        return str + "*1";
    }

    @Override
    public String methodTwo(String str) {
        return str + "*2";
    }

    @Override
    public String methodThree(String str) {
        return str + "*3";
    }
}
