package com.springcloud.consumerdemo1.mybatis_demo.mapper;

import com.springcloud.consumerdemo1.mybatis_demo.entity.DeptManager;
import com.springcloud.consumerdemo1.mybatis_demo.entity.DeptManagerExample;
import com.springcloud.consumerdemo1.mybatis_demo.entity.DeptManagerKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * DeptManagerDAO继承基类
 */
@Mapper
@Repository
public interface DeptManagerDAO extends MyBatisBaseDao<DeptManager, DeptManagerKey, DeptManagerExample> {
     List<DeptManager> getManagerSalaries();
}