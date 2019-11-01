package com.springcloud.consumerdemo1;

import com.alibaba.fastjson.JSON;
import com.springcloud.consumerdemo1.mybatis_demo.entity.DeptManager;
import com.springcloud.consumerdemo1.mybatis_demo.entity.Employees;
import com.springcloud.consumerdemo1.mybatis_demo.mapper.DeptManagerDAO;
import com.springcloud.consumerdemo1.mybatis_demo.mapper.EmployeesDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ConsumerDemo1ApplicationTests {

    @Autowired
    EmployeesDAO employeesDAO;

    @Autowired
    DeptManagerDAO deptManagerDAO;

    //@RequestMapping(value = "/mybatisTest")
    @Test
    public void mybatisTest(){
        //创建
        Employees sourceEmployees = new Employees();
        sourceEmployees.setFirstName("Leo");
        sourceEmployees.setLastName("Zeng");
        sourceEmployees.setGender("男");
        sourceEmployees.setHireDate(new Date(System.currentTimeMillis()+10000L));
        sourceEmployees.setBirthDate(new Date());

        //插入
        employeesDAO.insert(sourceEmployees);

        //取出
        Employees targetEmployees = employeesDAO.selectByPrimaryKey(1);
        log.info("插入结果[{}]", JSON.toJSONString(targetEmployees));
    }

    @Test
    public void testAsyncWithReturn() throws Exception {
        //查询
        List<DeptManager> deptManagers = deptManagerDAO.getManagerSalaries();
        //输出结果
        log.info("部门领导详情[{}]",deptManagers);
    }
}
