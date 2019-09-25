package com.springcloud.providerdemo2.feignTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: springcloud-example
 * @description:
 * @author:
 * @create: 2018-06-15 16:12
 **/
@Controller
public class FeignTestController {


    @GetMapping("/getUser")
    @ResponseBody
    public String getUser() {
        System.out.println("获取用户成功");
        return "{\"username\":\"张三\",\"age\":\"10\"}";
    }

}
