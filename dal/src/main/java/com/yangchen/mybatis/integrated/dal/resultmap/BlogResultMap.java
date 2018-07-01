package com.yangchen.mybatis.integrated.dal.resultmap;

import com.yangchen.mybatis.integrated.dal.dao.Author;
import lombok.Data;

/**
 * Created by James
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
@Data
public class BlogResultMap {

    private Integer bid;

    private String name;

    private Author author;
}
