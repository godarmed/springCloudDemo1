package com.springcloud.providerdemo1.excel_demo.excel_util;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ImportInfo extends BaseRowModel {
    @ExcelProperty(index = 0)
    @ApiModelProperty(value = "商家昵称")
    @NotBlank
    private String merchantName;

    @ExcelProperty(index = 1)
    @ApiModelProperty(value = "端口号")
    @NotBlank
    private String port;

    @ExcelProperty(index = 2)
    @ApiModelProperty(value = "签名")
    @NotBlank
    private String sign;

    @ExcelProperty(index = 3)
    @ApiModelProperty(value = "用途")
    private String usage;

    @ExcelProperty(index = 4)
    @ApiModelProperty(value = "所属省份")
    @NotBlank
    private String province;

    @ExcelProperty(index = 5)
    @ApiModelProperty(value = "授权证明")
    @NotBlank
    private String authorizationFiles;

    @ExcelProperty(index = 6)
    @ApiModelProperty(value = "有效期开始时间")
    @NotBlank
    private String validStart;

}
