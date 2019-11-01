package com.springcloud.consumerdemo1.mybatis_demo.mapper;

import com.springcloud.consumerdemo1.mybatis_demo.entity.Salaries;
import com.springcloud.consumerdemo1.mybatis_demo.entity.SalariesExample;
import com.springcloud.consumerdemo1.mybatis_demo.entity.SalariesKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * SalariesDAO继承基类
 */
@Mapper
@Repository
public interface SalariesDAO extends MyBatisBaseDao<Salaries, SalariesKey, SalariesExample> {
}