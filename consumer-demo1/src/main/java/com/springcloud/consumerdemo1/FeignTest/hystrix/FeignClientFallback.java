package com.springcloud.consumerdemo1.FeignTest.hystrix;

import com.eseasky.core.starters.feign.wrapper.exception.HystrixException;
import com.eseasky.core.starters.feign.wrapper.fallbacks.IHystrix;
import com.springcloud.consumerdemo1.FeignTest.leo_interface.FeignTestInterface;
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
public class FeignClientFallback implements  FeignTestInterface, IHystrix {
    private Throwable throwable;

    @Override
    public Throwable setThrowable(Throwable throwable) {
        this.throwable = throwable;
        return null;
    }

    public String getUser() {
        log.info("getUser方法调用失败!");
        throw new HystrixException(500,throwable.getMessage());
        //return "getUser方法调用失败!";
    }

    public String taskExecute() {
        log.info("taskExecute方法调用失败!");
        return "taskExecute方法调用失败!";
    }

    @Override
    public MultipartFile getFile() {
        log.info("taskExecute方法调用失败!");
        return null;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        log.info("taskExecute方法调用失败!");
        return null;
    }

    @Override
    public ResultModel<List<String>> uploadFiles(MultipartFile[] files) {
        log.info("taskExecute方法调用失败!");
        return null;
    }
}
