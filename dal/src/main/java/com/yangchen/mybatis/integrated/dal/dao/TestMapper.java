package com.yangchen.mybatis.integrated.dal.dao;

import java.util.List;

public interface TestMapper {
    Integer insert(Test record);

    Integer insertBatch(List<Test> list);

    List<Test> selectAll();
}