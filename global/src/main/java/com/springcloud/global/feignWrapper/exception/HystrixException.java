package com.springcloud.global.feignWrapper.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.springcloud.global.exception.error.IException;

/**
 * 自定义不进入熔断的异常类
 */
public class HystrixException extends HystrixBadRequestException implements IException{
    private static final long serialVersionUID = 1L;
    private Integer code = 500;
    private String msg = null;

    public HystrixException(String message) {
        super(message);
        this.setMsg(message);
    }

    public HystrixException(int code, String message) {
        super(message);
        this.setCode(code);
        this.setMsg(message);
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
