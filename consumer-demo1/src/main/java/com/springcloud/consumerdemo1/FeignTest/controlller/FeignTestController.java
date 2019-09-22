package com.springcloud.consumerdemo1.FeignTest.controlller;

import com.springcloud.consumerdemo1.FeignTest.leo_interface.FeignTestInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @program: springcloud-example
 * @description:
 * @author:
 * @create: 2018-06-15 15:55
 **/
@Controller
@Slf4j
public class FeignTestController{
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FeignTestInterface feignTestInterface;

    @RequestMapping("/getUser")
    @ResponseBody
    public String getUser() {
        return feignTestInterface.getUser();
    }

    //远程文件下载
    @RequestMapping("/getFile")
    public MultipartFile getFile() {
        MultipartFile multipartFile = feignTestInterface.getFile();
        log.info("文件名为:[{}]",multipartFile.getName());
        return multipartFile;
    }
    private final static String fileName = "C:/Users/zzy/Desktop/20190901曾志远周报.pdf";

    //远程文件上传
    @RequestMapping("/uploadFile")
    public String uploadFile() {
        //获取文件，转换为MuktipartFile形式
        File sourceFile = new File(fileName);
        //确认文件是否存在
        if(sourceFile.exists()){
            log.info("文件绝对路径为：[{}]",sourceFile.getAbsolutePath());
        }
        //文件转化
        MultipartFile targetFile = null;
        try {
            targetFile = new MockMultipartFile(sourceFile.getName(), new FileInputStream(sourceFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //上传文件
        return feignTestInterface.uploadFile(targetFile);
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