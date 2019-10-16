package com.springcloud.consumerdemo1.FeignTest.controlller;

import com.springcloud.consumerdemo1.FeignTest.service.ClientFallbackFeign;
import com.springcloud.global.entity.DTO.StudentDTO;
import com.springcloud.global.entity.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @program: springcloud-example
 * @description:
 * @author:
 * @create: 2018-06-15 15:55
 **/
@RestController
@Slf4j
public class FeignConsumerController{
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ClientFallbackFeign clientFallbackFeign;

    @RequestMapping("/getUser")
    @ResponseBody
    public String getUser() {
        return clientFallbackFeign.getUser();
    }

    @RequestMapping("/setUser")
    @ResponseBody
    public String setUser(@RequestBody StudentDTO studentDTO) {
        return clientFallbackFeign.setUser(studentDTO);
    }


    //远程文件下载
    @RequestMapping("/getFile")
    public MultipartFile getFile() {
        MultipartFile multipartFile = clientFallbackFeign.getFile();
        log.info("文件名为:[{}]",multipartFile.getName());
        return multipartFile;
    }

    //多文件上传
    @RequestMapping(value = "/uploadFiles")
    public ResultModel<List<String>> uploadFiles(@Valid StudentDTO studentDTO,HttpServletRequest req) {
        //获取HttpRequest中的文件
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        List<MultipartFile> targetfiles = multipartRequest.getFiles("file");
        //上传文件
        return clientFallbackFeign.uploadFiles(studentDTO.getGender(),studentDTO.getName(), targetfiles);
    }

    @GetMapping("/task")
    public String taskExecute() {
        return clientFallbackFeign.taskExecute();
    }

    @GetMapping("/timeTask")
    public String timeTaskExecute(Integer taskTime) {
        return clientFallbackFeign.timeTaskExecute(taskTime);
    }

    @GetMapping("/loadInstance")
    public String loadInstance() {
        ServiceInstance choose = this.loadBalancerClient.choose("provider-8783");
        System.out.println(choose.getServiceId()+":"+choose.getHost()+":"+choose.getPort());
        return choose.getServiceId() + ":" + choose.getHost() + ":" + choose.getPort();
    }

}