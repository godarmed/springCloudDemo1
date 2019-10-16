package com.springcloud.consumerdemo1.FeignTest.service.hystrix;

import com.springcloud.consumerdemo1.FeignTest.service.ClientFallbackFeign;
import com.springcloud.global.feignWrapper.exception.HystrixException;
import com.springcloud.global.feignWrapper.fallbacks.IHystrix;
import com.springcloud.global.entity.DTO.StudentDTO;
import com.springcloud.global.entity.ResultModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description: 回调实现类
 * @Param:
 * @return:
 * @Author:
 * @Date: 2019/9/18
 */

@Slf4j
@Service
public class ClientFallbackFeignHystrix implements ClientFallbackFeign,IHystrix{
    private Throwable throwable;

    @Override
    public Throwable setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return null;
    }

    @Override
    public String getUser() {
        log.info("getUser方法调用失败!");
        throw new HystrixException(500,throwable.getMessage());
        //return "getUser方法调用失败!";
    }

    @Override
    public String taskExecute() {
        log.info("taskExecute方法调用失败!");
        return "taskExecute方法调用失败!";
    }

    @Override
    public String setUser(StudentDTO studentDTO) {
        log.info("setUser方法调用失败!");
        throw new HystrixException(500,throwable.getMessage());
        //return "setUser方法调用失败!";
    }

    @Override
    public String timeTaskExecute(Integer taskTime) {
        return null;
    }

    @Override
    public MultipartFile getFile() {
        return null;
    }

    @Override
    public ResultModel<List<String>>  uploadFiles(String id, String token, List<MultipartFile> file) {
        throw new HystrixException(500,throwable.getMessage());
        //return null;
    }

}
