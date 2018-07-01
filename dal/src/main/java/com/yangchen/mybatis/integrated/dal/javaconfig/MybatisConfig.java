package com.yangchen.mybatis.integrated.dal.javaconfig;

import com.github.pagehelper.PageInterceptor;
import com.yangchen.mybatis.integrated.dal.typehandlers.TestTypeHandler;
import org.apache.ibatis.executor.loader.cglib.CglibProxyFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Author: cyanga
 * @Description:
 * @Date: 11:31 2018/6/26
 */
@Configuration
@MapperScan(basePackages = "com.yangchen.mybatis.integrated.dal")
@EnableTransactionManagement(proxyTargetClass = true)
public class MybatisConfig {
    @Autowired
    @Qualifier("dataSource")
    public DataSource dataSource;

    @Lazy(false)
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory localSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        sqlSessionFactoryBean.setTypeHandlers(new TypeHandler[]{new TestTypeHandler()});
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor()});

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
        // 嵌套查询时，需要懒加载
        sqlSessionFactory.getConfiguration().setLazyLoadingEnabled(true);
        sqlSessionFactory.getConfiguration().setAggressiveLazyLoading(false);
        sqlSessionFactory.getConfiguration().setProxyFactory(new CglibProxyFactory());

        return sqlSessionFactory;
    }

    private PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        pageInterceptor.setProperties(properties);

        return pageInterceptor;
    }

    @Primary
    @Lazy
    @Bean(name = "simple")
    public SqlSessionTemplate simple() throws Exception {
        return new SqlSessionTemplate(localSessionFactoryBean(), ExecutorType.SIMPLE);
    }

    @Lazy
    @Bean(name = "batchSet")
    public SqlSessionTemplate batchSet() throws Exception {
        return new SqlSessionTemplate(localSessionFactoryBean(), ExecutorType.BATCH);
    }

    @Bean(name = "dataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);

        return dataSourceTransactionManager;
    }
}
