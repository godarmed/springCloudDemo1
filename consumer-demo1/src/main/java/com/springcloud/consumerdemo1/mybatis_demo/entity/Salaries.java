package com.springcloud.consumerdemo1.mybatis_demo.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * salaries
 * @author 
 */
@Data
public class Salaries extends SalariesKey implements Serializable {
    private Integer salary;

    private Date toDate;

    private static final long serialVersionUID = 1L;
}