package com.yangchen.mybatis.integrated.dal;

import com.yangchen.mybatis.integrated.dal.dao.BlogMapper;
import com.yangchen.mybatis.integrated.dal.dao.Posts;
import com.yangchen.mybatis.integrated.dal.javaconfig.DataSourceConfig;
import com.yangchen.mybatis.integrated.dal.javaconfig.MybatisConfig;
import com.yangchen.mybatis.integrated.dal.resultmap.BlogPostsResultMap;
import com.yangchen.mybatis.integrated.dal.resultmap.BlogResultMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, MybatisConfig.class})
@Slf4j
public class BlogMapperTest {
    private final static Logger log = LoggerFactory.getLogger(BlogMapperTest.class);
    @Autowired
    private BlogMapper blogMapper;

    //one to one 嵌套结果
    @Test
    public void selectBlogAuthor() {
        BlogResultMap resultMap = blogMapper.selectBlogAuthor2(1);
        System.out.println(resultMap);
    }

    //one to one 嵌套查询
    @Test
    public void selectBlogAuthor2() {
        BlogResultMap resultMap = blogMapper.selectBlogAuthor(1);
        System.out.println(resultMap);
    }

    @Test
    public void selectByBlogId() {
        List<Posts> resultMap = blogMapper.selectByBlogId(1);
        System.out.println(resultMap);
    }

    //one to many 嵌套结果
    @Test
    public void selectBlogPosts() {
        BlogPostsResultMap resultMap = blogMapper.selectBlogPosts2(1);
        System.out.println(resultMap);
    }

    //one to many 嵌套查询
    @Test
    public void selectBlogPosts2() throws InterruptedException {
        BlogPostsResultMap resultMap = blogMapper.selectBlogPosts(1);
        System.out.println(resultMap.getName());
        Thread.sleep(5000);
        System.out.println(resultMap.getPosts().get(0).getPostName());
    }

    //N+1问题
    @Test
    public void selectBlogPosts嵌套查询N1() throws InterruptedException {//one to many 嵌套查询
        //这里是1次
        List<BlogPostsResultMap> resultMap = blogMapper.selectBlogPostsList(0);
        System.out.println(resultMap.get(0).getName());
        Thread.sleep(5000);
        //当要使用的时候他们再去拉取数据 这里就是N次
        System.out.println(resultMap.get(0).getPosts().get(0).getPostName());
        System.out.println(resultMap.get(1).getPosts().get(0).getPostName());
        //1次 + N次
    }

    @Test
    public void selectByAuthorId2() {//one to one 嵌套结果
        BlogResultMap resultMap = blogMapper.selectBlogAuthor2(1);
        System.out.println(resultMap);
    }

}
