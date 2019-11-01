package com.springcloud.consumerdemo1.mybatis_demo.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * salaries
 * @author 
 */
@Data
public class SalariesKey implements Serializable {
    private Integer empNo;

    private Date fromDate;

    private static final long serialVersionUID = 1L;
}