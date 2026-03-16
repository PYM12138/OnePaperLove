package com.pym.mingblog.service;

import com.pym.mingblog.model.ArticleOperationLog;

import java.util.List;

/**
 * @Author: Ming
 * @Date 2025/4/22 16:01
 * @Description: 操作记录
 */

public interface ArticleOperationLogService {

    /**
     * 查询文章操作记录
     * @return 文章操作记录集合
     */
    List<ArticleOperationLog> findArticleLogAll();

    /**
     * 插入文章操作记录
     * @param log 文章操作记录
     * @return 插入行数
     */
    Integer insertOneArticleLog(ArticleOperationLog log);
}
