package com.springcloud.consumerdemo1.mybatis_demo.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * dept_manager
 * @author 
 */
@Data
public class DeptManager extends DeptManagerKey implements Serializable {
    private Date fromDate;

    private Date toDate;

    private Salaries salaries;

    private static final long serialVersionUID = 1L;
}