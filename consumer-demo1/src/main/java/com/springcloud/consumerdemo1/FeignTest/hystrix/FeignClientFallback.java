package com.springcloud.consumerdemo1.FeignTest.hystrix;

import com.springcloud.consumerdemo1.FeignTest.leo_interface.FeignTestInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description: 回调实现类
 * @Param:
 * @return:
 * @Author:
 * @Date: 2019/9/18
 */

@Slf4j
@Service
public class FeignClientFallback{
    public String getUser() {
        log.info("getUser方法调用失败!");
        return "getUser方法调用失败!";
    }

    public String taskExecute() {
        log.info("taskExecute方法调用失败!");
        return "taskExecute方法调用失败!";
    }
}
