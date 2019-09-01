package com.springcloud.providerdemo1.service.service_one;

import com.springcloud.providerdemo1.strategy.RouteStrategy;
import org.springframework.stereotype.Service;

@Service
public class AddService implements RouteStrategy {
    @Override
    public String methodOne(String str) {
        return str + "+1";
    }

    @Override
    public String methodTwo(String str) {
        return str + "+2";
    }

    @Override
    public String methodThree(String str) {
        return str + "+3";
    }
}
