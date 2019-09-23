package com.springcloud.providerdemo1.feignTest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.*;

/**
 * @program: springcloud-example
 * @description:
 * @author:
 * @create: 2018-06-15 16:12
 **/
@RestController
@Slf4j
public class FeignTestController {


    @GetMapping("/getUser")
    public String getUser() {
        System.out.println("获取用户成功");
        return "{\"username\":\"张三\",\"age\":\"10\"}";
    }

    private final static String fileName = "C:/Users/zzy/Desktop/20190901曾志远周报.pdf";

    @RequestMapping(value="/getFile",method = {RequestMethod.POST})
    public MultipartFile getFile(){
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
        //返回文件
        return targetFile;
    }

    @RequestMapping(value = "/uploadFile",method = {RequestMethod.POST},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestPart(value="file") MultipartFile file){
        //返回文件名
        try {
            file.getInputStream();
            log.info("文件名为[{}]",file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getName();
    }

    public static void main(String[] args) {
        FeignTestController feignTestController = new FeignTestController();
        MultipartFile multipartFile = feignTestController.getFile();
        log.info("文件名为:[{}]",multipartFile.getName());
    }
}
