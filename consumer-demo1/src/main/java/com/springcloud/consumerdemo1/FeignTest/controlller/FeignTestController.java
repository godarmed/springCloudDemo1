package com.springcloud.consumerdemo1.FeignTest.controlller;

import com.springcloud.consumerdemo1.FeignTest.leo_interface.FeignTestInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @program: springcloud-example
 * @description:
 * @author:
 * @create: 2018-06-15 15:55
 **/
@RestController
public class FeignTestController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FeignTestInterface feignTestInterface;

    @RequestMapping("/getUser")
    public String getUser() {
        return feignTestInterface.getUser();
    }

    @GetMapping("/task")
    public String taskExecute() {
        return feignTestInterface.taskExecute();
    }

    @GetMapping("/loadInstance")
    public String loadInstance() {
        ServiceInstance choose = this.loadBalancerClient.choose("provider-8783");
        System.out.println(choose.getServiceId()+":"+choose.getHost()+":"+choose.getPort());
        return choose.getServiceId() + ":" + choose.getHost() + ":" + choose.getPort();
    }

}