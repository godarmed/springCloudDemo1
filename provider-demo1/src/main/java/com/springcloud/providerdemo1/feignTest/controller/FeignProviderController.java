package com.springcloud.providerdemo1.feignTest.controller;

import com.alibaba.fastjson.JSON;
import com.springcloud.global.entity.DTO.StudentDTO;
import com.springcloud.global.entity.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: springcloud-example
 * @description:
 * @author:
 * @create: 2018-06-15 16:12
 **/
@RestController
@Profile({"dev","test"})
@Slf4j
public class FeignProviderController {

    @Value("${img.resource-path}")
    private String IMG_PATH;


    @GetMapping("/getUser")
    public String getUser() {
        System.out.println("获取用户成功");
        return "{\"username\":\"张三\",\"age\":\"10\"}";
    }

    @PostMapping("/setUser")
    public String getUser(@RequestBody @Valid StudentDTO studentDTO) throws InterruptedException {
        System.out.println("保存用户成功");
        return JSON.toJSONString(studentDTO);
    }

    private final static String fileName = "C:\\Users\\Administrator\\Desktop\\服务号开发相关\\图片样例\\1440,蓝.png";

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
            targetFile = new MockMultipartFile("file",sourceFile.getName(), MediaType.MULTIPART_FORM_DATA_VALUE, new FileInputStream(sourceFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回文件
        return targetFile;
    }

    @RequestMapping(value = "/uploadFile")
    public ResultModel<List<String>> uploadFiles(@RequestParam("id") String id,@RequestParam("token") String token,@RequestPart(value="file") List<MultipartFile> files){
        log.info("上传者id为[{}]",JSON.toJSONString(id));
        log.info("上传者token为[{}]",JSON.toJSONString(token));
        //保存文件
        List<File> targetFiles =  saveMultiPartFiles(files);
        //返回文件名数组
        ResultModel<List<String>> resultModel = new ResultModel<>();
        List<String> fileNames = new ArrayList<>();
        for (File file : targetFiles) {
            log.info("文件名为[{}]",file.getName());
            fileNames.add(file.getName());
        }
        resultModel.setData(fileNames);
        return resultModel;
    }


    private List<File> saveMultiPartFiles(List<MultipartFile> multipartFile){
        List<File> files = new ArrayList<>();
        for (MultipartFile file : multipartFile) {
            files.add(multipartFile2File(file,true));
        }
        return files;
    }

    private File multipartFile2File(MultipartFile multipartFile,Boolean... useNewName){
        //生成文件路径
        File path = new File(IMG_PATH);

        //如果路径不存在则生成路径
        if(!path.exists()){
            path.mkdirs();
        }

        //生成随机文件名
        String fileName = multipartFile.getOriginalFilename();
        if(useNewName.length > 0 && useNewName[0]){
            String fileSuffix = fileName.substring(fileName.indexOf("."));
            try {
                fileName = getFileMD5(multipartFile.getBytes()) + fileSuffix;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //创建文件
        File file = new File(IMG_PATH,fileName);

        //保存字节流
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    //获取文件的MD5值
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[4096];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(Hex.encodeHex(digest.digest()));
    }

    //获取byte[]的MD5值
    public static String getFileMD5(byte[] bytes) {
        if (bytes==null) {
            return null;
        }
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return new String(Hex.encodeHex(digest.digest()));
    }

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\images\\f130bc4685da46eba11fad47af9b15a5.png");
        MultipartFile multipartFile = new MockMultipartFile("file",file.getName(),MediaType.MULTIPART_FORM_DATA_VALUE,new BufferedInputStream(new FileInputStream(file)));
        log.info("md5计算结果一[{}]",getFileMD5(file));
        log.info("md5计算结果二[{}]",getFileMD5(multipartFile.getBytes()));
    }
}
