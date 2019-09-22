package com.springcloud.consumerdemo1.FeignTest.leo_interface;

import com.springcloud.consumerdemo1.FeignTest.config.FileFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Service
@FeignClient(name = "provider-8783",configuration = FileFeignConfig.class)
public interface FeignTestInterface {

    @RequestMapping("/getUser")
    public String getUser();

    @RequestMapping("/task")
    public String taskExecute();

    @RequestMapping(value = "/getFile",method = {RequestMethod.POST})
    public MultipartFile getFile();

    @RequestMapping(value = "/uploadFile",method = {RequestMethod.POST},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestPart(value="file") MultipartFile file);

}
