package com.pym.mingblog.service;

import java.util.List;

/**
 * @Author: Ming
 * @Date 2025/5/20 16:12
 * @Description: ArticleTag服务层(增删改查)以及结果返回包装
 */

public interface ArticleTagService {
    /**
     * 给文章保存标签（新增不存在的标签，绑定标签）
     * @param articleId 文章ID
     * @param tagNames 标签名称列表（前端传过来的字符串数组）
     */
    void saveTagsForArticle(Long articleId, List<String> tagNames);
}