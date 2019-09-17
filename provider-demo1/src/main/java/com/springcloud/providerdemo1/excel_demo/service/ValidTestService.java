package com.springcloud.providerdemo1.excel_demo.service;

import com.springcloud.providerdemo1.excel_demo.excel_util.ImportInfo;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Service
@Validated
public class ValidTestService {

    public ImportInfo getExcel(ImportInfo importInfo) throws IOException {
        return ((ValidTestService)AopContext.currentProxy()).getExcel2(importInfo);
        //return getExcel2(importInfo);
    }

    public ImportInfo getExcel2(@Valid ImportInfo importInfo) throws IOException {
        return importInfo;
    }
}

