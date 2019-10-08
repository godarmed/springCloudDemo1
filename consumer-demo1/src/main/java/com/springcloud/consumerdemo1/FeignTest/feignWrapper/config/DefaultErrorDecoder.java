package com.springcloud.consumerdemo1.FeignTest.feignWrapper.config;

import com.alibaba.fastjson.JSON;
import com.springcloud.consumerdemo1.FeignTest.feignWrapper.exception.HystrixException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.Map;

@Slf4j
//自定义异常解码器
public class DefaultErrorDecoder implements ErrorDecoder{
    Exception exception = null;
    @Override
    public Exception decode(String methodKey, Response response) {
        //获取原始的返回内容
        try {
            String errContext = Util.toString(response.body().asReader());
            //获取异常信息，以Map形式接受
            if(StringUtils.isNotBlank(errContext)){
                Map<String,Object> result = (Map)JSON.parseObject(errContext,Map.class);
                if (result.containsKey("code")) {
                    exception = new HystrixException((Integer)result.get("code"), String.valueOf(result.getOrDefault("message", "未知异常")));
                } else {
                    exception = new HystrixException(response.status(), errContext);
                }
            }else{
                exception = new HystrixException(response.status(), "未知异常");
            }
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            exception = new HystrixException(response.status(), e.getMessage());
        }
        return exception;
    }
}
