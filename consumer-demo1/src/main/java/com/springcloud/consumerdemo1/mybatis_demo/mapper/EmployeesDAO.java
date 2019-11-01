package com.springcloud.consumerdemo1.mybatis_demo.mapper;

import com.springcloud.consumerdemo1.mybatis_demo.entity.Employees;
import com.springcloud.consumerdemo1.mybatis_demo.entity.EmployeesExample;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * EmployeesDAO继承基类
 */
@Mapper
@Repository
public interface EmployeesDAO extends MyBatisBaseDao<Employees, Integer, EmployeesExample> {
}