package com.springcloud.consumerdemo1.FeignTest;

import com.springcloud.consumerdemo1.FeignTest.config.FeignConfig;
import com.springcloud.consumerdemo1.FeignTest.feignWrapper.fallbacks.FeignHystrixFactory;
import com.springcloud.global.entity.DTO.StudentDTO;
import com.springcloud.global.entity.ResultModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@FeignClient(name = "provider-8783",configuration = FeignConfig.class,fallbackFactory = FeignHystrixFactory.class)
public interface FeignTestInterface {

    @RequestMapping("/getUser")
    public String getUser();

    @RequestMapping("/setUser")
    public String setUser(@RequestBody StudentDTO studentDTO);

    @RequestMapping("/task")
    public String taskExecute();

    @RequestMapping("/timeTask")
    public String timeTaskExecute(@RequestBody Integer taskTime);

    @RequestMapping(value = "/getFile",method = {RequestMethod.POST})
    public MultipartFile getFile();

    @RequestMapping(value = "/uploadFile",method = {RequestMethod.POST},produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestPart(value="file") MultipartFile file);

    @RequestMapping(value = "/uploadFiles",method = {RequestMethod.POST},produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultModel<List<String>> uploadFiles(@RequestPart(value="file") MultipartFile[] files);

}
