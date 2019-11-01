package com.springcloud.consumerdemo1.mybatis_demo.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * employees
 * @author 
 */
@Data
public class Employees implements Serializable {
    private Integer empNo;

    private Date birthDate;

    private String firstName;

    private String lastName;

    private String gender;

    private Date hireDate;

    private static final long serialVersionUID = 1L;
}