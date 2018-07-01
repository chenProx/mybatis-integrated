package com.yangchen.mybatis.integrated.dal;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yangchen.mybatis.integrated.dal.dao.TestMapper;
import com.yangchen.mybatis.integrated.dal.javaconfig.DataSourceConfig;
import com.yangchen.mybatis.integrated.dal.javaconfig.MybatisConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, MybatisConfig.class})
@Slf4j
public class TestMapperTest {
//    private final static Logger log = LoggerFactory.getLogger(TestMapperTest.class);
    @Autowired
    private TestMapper testMapper;

    @Autowired
    @Qualifier("batchSet")
    private SqlSessionTemplate sqlSessionTemplate;

    @Test
    @Rollback(false)
    public void insert() {
        com.yangchen.mybatis.integrated.dal.dao.Test test = new com.yangchen.mybatis.integrated.dal.dao.Test();
        test.setName("ShenPiPi");
        test.setNums(7);
        int key = testMapper.insert(test);
        System.out.println(key);
        System.out.println(test.getId());

        log.info("插入新纪录返回主键", key);
        log.info("主键本键", test.getId());
    }

    @Test
    @Transactional
    public void insertBatchByCodingFor() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            com.yangchen.mybatis.integrated.dal.dao.Test test = new com.yangchen.mybatis.integrated.dal.dao.Test();
            test.setName("ShenPiPi");
            test.setNums(i);
            testMapper.insert(test);
        }
        log.info("编码for耗时", System.currentTimeMillis() - start);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void insertBatchBySqlFor() {
        long start = System.currentTimeMillis();
        List<com.yangchen.mybatis.integrated.dal.dao.Test> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            com.yangchen.mybatis.integrated.dal.dao.Test test = new com.yangchen.mybatis.integrated.dal.dao.Test();
            test.setName("ShenPiPi");
            test.setNums(i);
            list.add(test);
        }
        testMapper.insertBatch(list);
        log.info("sql for 耗时", System.currentTimeMillis() - start);
    }

    @Test
    @Transactional
    public void insertBatchExecutorType() {
        SqlSession sqlSession =
                sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            com.yangchen.mybatis.integrated.dal.dao.Test test = new com.yangchen.mybatis.integrated.dal.dao.Test();
            test.setName("ShenPiPi");
            test.setNums(i);
            testMapper.insert(test);
            if (i != 0 && i % 5 == 0) {
                sqlSession.commit();
                sqlSession.clearCache();
            }
        }
        log.info("批处理sql耗时", System.currentTimeMillis() - start);
    }


    @Test
    public void pagination() {
        PageInfo<Object> pageInfo = PageHelper.startPage(1, 10).
                doSelectPageInfo(() -> testMapper.selectAll());
        log.info("分页结果", pageInfo);
    }
}
