package com.springcloud.consumerdemo1.mybatis_demo.controller;

import com.alibaba.fastjson.JSON;
import com.springcloud.consumerdemo1.mybatis_demo.entity.DeptManager;
import com.springcloud.consumerdemo1.mybatis_demo.entity.DeptManagerKey;
import com.springcloud.consumerdemo1.mybatis_demo.entity.Employees;
import com.springcloud.consumerdemo1.mybatis_demo.mapper.DeptManagerDAO;
import com.springcloud.consumerdemo1.mybatis_demo.mapper.EmployeesDAO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@RestController
public class MybatisTestController {
    @Autowired
    EmployeesDAO employeesDAO;

    @Autowired
    DeptManagerDAO deptManagerDAO;

    @RequestMapping(value = "/mybatisTest")
    public List<DeptManager> mybatisTest() {
        //查询
        List<DeptManager> deptManagers = deptManagerDAO.getManagerSalaries();
        //输出结果
        log.info("部门领导详情[{}]",deptManagers);
        return  deptManagers;
    }

    @RequestMapping(value = "/timeTest")
    public DeptManager mybatisTest(@RequestBody DeptManager deptManager) {
        //输出结果
        log.info("部门领导详情[{}]",deptManager);
        DeptManagerKey deptManagerKey = new DeptManager();
        BeanUtils.copyProperties(deptManager,deptManagerKey);
        if(deptManagerDAO.selectByPrimaryKey(deptManagerKey) != null){
            deptManagerDAO.deleteByPrimaryKey(deptManagerKey);
        }
        deptManagerDAO.insert(deptManager);
        return  deptManager;
    }
}
