package com.pym.mingblog.service;


import com.pym.mingblog.model.Article;
import com.pym.mingblog.utils.DataMap;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ming
 * @Date 2023/7/30 16:42
 * @Description: 文章的增删改查及记录查询
 */
public interface ArticleService {

    /**
     * 分页查询所有文章
     *
     * @param rows    一页显示文章数
     * @param pageNum 当前页码
     * @return 该页所有文章
     */
    DataMap<?> findAllArticles(int rows, int pageNum);

    /**
     * 文章ID查询文章
     *
     * @param articleId 文章ID
     * @return 文章信息
     */
    @Transactional
    //开启事务就是开启了一级缓存
    DataMap<?> findArticleById(long articleId);

    /**
     * 文章ID查询文章标题和文章摘要
     *
     * @param articleId 文章ID
     * @return 文章信息
     */
    Map<String, String> getArticleTitleAndSummary(long articleId);

    /**
     * 插入文章
     * @param article 文章
     **/
    void addArticle(Article article);


    /**
     * 更新文章删除标志
     *
     * @param articleId 文章ID
     * @param isDelete  删除标志
     * @return 更新行数
     */
    Integer deleteArticle(Long articleId, Integer isDelete);

    /**
     * 更新文章
     *
     * @param article 文章数据
     * @return 更新行数
     */
    Integer updateArticle(Article article);

    /**
     * 文章ID查询文章ID
     *
     * @param articleId 文章ID
     * @return 文章ID
     */
    Long getArticleId(Long articleId);

    /**
     * 查询所有的文章包括分类和标签
     */
    List<Article> findAllArticlesWithCategoryAndTags();

    /**
     * 查询标签下对应的文章信息
     * @param tagName 标签名
     * @return 标签下对应的文章信息列表
     */
    List<Article> getArticlesByTagName(String tagName);


}
