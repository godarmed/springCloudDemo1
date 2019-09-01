package com.springcloud.providerdemo1.controller;


import com.springcloud.providerdemo1.service.service_one.AddService;
import com.springcloud.providerdemo1.service.service_two.MulService;
import com.springcloud.providerdemo1.strategy.RouteContext;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="策略模式测试接口")
@Slf4j
public class StrategyTestController{
    @Autowired
    RouteContext routeContext;

    @Autowired
    AddService addService;

    @Autowired
    MulService mulService;

    private void initContext(String strategySign){
        if(StringUtils.isNotBlank(strategySign)){
            switch(strategySign){
                case "addStrategy":
                    routeContext.setRouteStrategy(addService);
                    break;
                case "mulStrategy":
                    routeContext.setRouteStrategy(mulService);
                    break;
                default:
                    routeContext.setRouteStrategy(addService);
                    break;
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
