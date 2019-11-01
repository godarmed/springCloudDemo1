package com.springcloud.consumerdemo1.mybatis_demo.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * dept_manager
 * @author 
 */
@Data
public class DeptManagerKey implements Serializable {
    private Integer empNo;

    private String deptNo;

    private static final long serialVersionUID = 1L;
}