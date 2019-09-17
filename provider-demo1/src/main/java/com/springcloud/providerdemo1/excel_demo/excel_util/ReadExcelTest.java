package com.springcloud.providerdemo1.excel_demo.excel_util;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class ReadExcelTest {
    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
    public Object readExcel(MultipartFile excel) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(excel.getInputStream());
        return ExcelUtil.readExcel(bufferedInputStream,ImportInfo.class,1);
    }
}
