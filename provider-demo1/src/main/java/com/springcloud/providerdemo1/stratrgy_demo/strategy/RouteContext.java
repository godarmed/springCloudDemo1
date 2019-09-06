package com.springcloud.providerdemo1.stratrgy_demo.strategy;

import org.springframework.stereotype.Service;

@Service
public class RouteContext {
    private RouteStrategy routeStrategy;

    public void setRouteStrategy(RouteStrategy routeStrategy) {
        this.routeStrategy = routeStrategy;
    }

    public String executeMethodOne(String str){
        return routeStrategy.methodOne(str);
    }

    public String executeMethodTwo(String str){
        return routeStrategy.methodTwo(str);
    }

    public String executeMethodThree(String str){
        return routeStrategy.methodThree(str);
    }
}
