package com.springcloud.consumerdemo1.FeignTest.leo_interface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(name = "provider-8783")
public interface FeignTestInterface {
    @RequestMapping("/getUser")
    public String getUser();

    @RequestMapping("/task")
    public String taskExecute();
}
