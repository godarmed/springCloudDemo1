package com.springcloud.providerdemo1.stratrgy_demo.controller;


import com.springcloud.global.utils.SpringUtils;
import com.springcloud.providerdemo1.stratrgy_demo.service.service_one.AddService;
import com.springcloud.providerdemo1.stratrgy_demo.service.service_two.MulService;
import com.springcloud.providerdemo1.stratrgy_demo.strategy.RouteContext;
import com.springcloud.providerdemo1.stratrgy_demo.strategy.RouteStrategy;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@Api(value="策略模式测试接口")
@Slf4j
public class StrategyTestController{
    @Autowired
    RouteContext routeContext;

    @Autowired
    AddService addService;

    private void initContext(String strategySign){
        if(StringUtils.isNotBlank(strategySign)){
            Collection<RouteStrategy> beans = SpringUtils.getBeanOfType(RouteStrategy.class);
            for (RouteStrategy bean : beans) {
                if(bean.isThisStrategy(strategySign)){
                    routeContext.setRouteStrategy(bean);
                    break;
                }
            }
            if(routeContext == null){
                routeContext.setRouteStrategy(addService);
            }
        }else{
            throw new RuntimeException("未选择策略");
        }
    }

    @PostMapping("/test/methodOne")
    public String methodOne(@RequestParam String request, @RequestParam String strategySign) {
        initContext(strategySign);
        String result = routeContext.executeMethodOne(request);
        return result;
    }

    @PostMapping("/test/methodTwo")
    public String methodTwo(String request,String strategySign) {
        initContext(strategySign);
        String result = routeContext.executeMethodTwo(request);
        return result;
    }

    @PostMapping("/test/methodThree")
    public String methodThree(String request,String strategySign) {
        initContext(strategySign);
        String result = routeContext.executeMethodThree(request);
        return result;
    }


}
