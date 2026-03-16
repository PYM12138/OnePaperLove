package com.pym.mingblog.service;

import com.pym.mingblog.model.Article;
import com.pym.mingblog.model.Category;
import com.pym.mingblog.utils.DataMap;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ming
 * @Date 2023/8/10 0:02
 * @Description: 分类
 */
public interface CategoryService {
    /**
     * 按分类归类文章，除了学习tag的文章
     * @param categoryName 分类名
     * @return 所有相关的分类的文章
     * */
    DataMap sortArticleByCategoryName(String categoryName);

    /**
     * 按学习标签归类文章
     */
    DataMap sortArticleByCategoryNameForLearn(String categoryName);

    /**
     * 查询所有的分类
     * @return 所有的分类
     */
    DataMap findAllCategories();

    /**
     * 添加分类
     * @param category 分类实体
     */
    Integer addCategory(Category category);


    //文章按照 分类进行分类
    Map<String, List<Article>> groupByCategory(List<Article> articles);
    //文章按照月份进行分类
    Map<String, List<Article>> groupByMonth(List<Article> articles);

    //根据标签来查询文章分类
    List<Category> getCategoriesByTagName(String tagName);

}
