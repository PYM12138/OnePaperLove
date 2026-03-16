package com.pym.mingblog.service.Impl;

import com.pym.mingblog.mapper.ArticleOperationLogMapper;
import com.pym.mingblog.model.ArticleOperationLog;
import com.pym.mingblog.service.ArticleOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Ming
 * @Date 2025/4/22 16:40
 * @Description: 文章操作日志服务实现类
 */
@Service
public class ArticleLogServiceImpl implements ArticleOperationLogService {
    @Autowired
    ArticleOperationLogMapper articleOperationLogMapper;

    @Override
    public List<ArticleOperationLog> findArticleLogAll() {

        return articleOperationLogMapper.findAll();
    }

    @Override
    public Integer insertOneArticleLog(ArticleOperationLog log) {

        return articleOperationLogMapper.insertOne(log);
    }

}
