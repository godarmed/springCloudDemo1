package com.springcloud.providerdemo1.excel_demo.controller;

import com.springcloud.providerdemo1.excel_demo.excel_util.ExcelUtil;
import com.springcloud.providerdemo1.excel_demo.excel_util.ImportInfo;
import com.springcloud.providerdemo1.excel_demo.service.ValidTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class ExcelTestController {
    @Autowired
    ValidTestService validTestService;

    @PostMapping("/getExcel")
    public List<ImportInfo> getExcel(MultipartFile file) throws IOException {
        List<ImportInfo> list = ExcelUtil.readExcel(new BufferedInputStream(file.getInputStream()),ImportInfo.class,0);
        return list;
    }

    @PostMapping("/getInfo")
    public ImportInfo getInfo() throws IOException {
        ImportInfo importInfo = new ImportInfo();
        return validTestService.getExcel(importInfo) ;
    }


}
