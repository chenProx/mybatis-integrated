package com.yangchen.mybatis.integrated.dal.dao;

import com.yangchen.mybatis.integrated.dal.resultmap.BlogPostsResultMap;
import com.yangchen.mybatis.integrated.dal.resultmap.BlogResultMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper {
    Blog selectByPrimaryKey(Integer bid);
    //嵌套查询
    BlogResultMap selectBlogAuthor(Integer bid);
    //嵌套结果
    BlogResultMap selectBlogAuthor2(Integer bid);
    //嵌套结果
    BlogPostsResultMap selectBlogPosts2(Integer bid);
    //嵌套查询
    BlogPostsResultMap selectBlogPosts(Integer bid);
    //嵌套查询
    //大于指定id
    List<BlogPostsResultMap> selectBlogPostsList(Integer bid);

    List<Posts> selectByBlogId(Integer bid);
}