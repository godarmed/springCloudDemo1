package com.springcloud.global.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

@Slf4j
public class HttpUtil {
    public HashMap getHeaders(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Enumeration<String> names = request.getHeaderNames();
        String key = null;
        HashMap params = new HashMap();

        while(names.hasMoreElements()) {
            key = (String)names.nextElement();
            params.put(key, request.getHeader(key));
        }

        log.info("所有的请求头为:[{}]",params);
        return params;
    }
}
