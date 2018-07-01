package com.yangchen.mybatis.integrated.dal.resultmap;

import com.yangchen.mybatis.integrated.dal.dao.Posts;
import lombok.Data;

import java.util.List;

/**
 * Created by James
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
@Data
public class BlogPostsResultMap{

    private Integer bid;

    private String name;

    private Integer authorId;

    private List<Posts> posts;

}
