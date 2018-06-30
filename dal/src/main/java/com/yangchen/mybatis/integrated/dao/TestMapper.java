package com.yangchen.mybatis.integrated.dao;

import java.util.List;

public interface TestMapper {
    int insert(Test record);

    int insertBatch(List<Test> list);

    List<Test> selectAll();
}