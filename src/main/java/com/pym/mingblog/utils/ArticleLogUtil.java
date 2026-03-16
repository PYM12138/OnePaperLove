package com.pym.mingblog.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pym.mingblog.mapper.ArticleOperationLogMapper;
import com.pym.mingblog.model.ArticleOperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ming
 * @Date 2025/4/25 21:20
 * @Description:
 */
@Component
public class ArticleLogUtil {

    @Autowired
    private ArticleOperationLogMapper logMapper;

    /**
     * 插入操作日志
     */
    public void log(Long articleId, String userName, String operationType, String description) {
        ArticleOperationLog log = new ArticleOperationLog();
        log.setArticleId(articleId);
        log.setUserName(userName);
        log.setOperationType(operationType);
        log.setOperationTime(LocalDateTime.now());
        log.setDescription(description);
        logMapper.insertOne(log);
    }

    /**
     * 查询操作日志（支持模糊搜索 + 分页）
     */

    public DataMap<?> searchLogs(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ArticleOperationLog> logs = logMapper.searchLogs(keyword);
        PageInfo<ArticleOperationLog> pageInfo = new PageInfo<>(logs);

        // 封装日志列表
        List<Map<String, Object>> logList = new ArrayList<>();
        for (ArticleOperationLog log : logs) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("id", log.getId());
            logMap.put("articleId", log.getArticleId());
            logMap.put("userName", log.getUserName());
            logMap.put("operationType", log.getOperationType());
            logMap.put("operationTime", log.getOperationTime());
            logMap.put("description", log.getDescription());
            logList.add(logMap);
        }

        // 封装分页信息
        Map<String, Object> pageMeta = new HashMap<>();
        pageMeta.put("pageNum", pageInfo.getPageNum());
        pageMeta.put("pageSize", pageInfo.getPageSize());
        pageMeta.put("total", pageInfo.getTotal());
        pageMeta.put("pages", pageInfo.getPages());
        pageMeta.put("isFirstPage", pageInfo.isIsFirstPage());
        pageMeta.put("isLastPage", pageInfo.isIsLastPage());

        // 整合返回体
        Map<String, Object> result = new HashMap<>();
        result.put("logs", logList);
        result.put("pageInfo", pageMeta);

        return DataMap.success().setData(result);
    }

    /**
     * 查询所有操作日志，按时间排序（分页支持）
     */

    public DataMap<?> findAllLogs(int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询日志
        List<ArticleOperationLog> logs = logMapper.findAll();
        PageInfo<ArticleOperationLog> pageInfo = new PageInfo<>(logs);

        // 构建日志内容列表（可选字段映射，若无需转换可直接用 logs）
        List<Map<String, Object>> logList = new ArrayList<>();

        for (ArticleOperationLog log : logs) {
            Map<String, Object> logMap = new HashMap<>();
            logMap.put("id", log.getId());
            logMap.put("articleId", log.getArticleId());
            logMap.put("userName", log.getUserName());
            logMap.put("operationType", log.getOperationType());
            logMap.put("operationTime", log.getOperationTime());
            logMap.put("description", log.getDescription());
            logList.add(logMap);
        }

        // 构建分页信息
        Map<String, Object> pageMeta = new HashMap<>();
        pageMeta.put("pageNum", pageInfo.getPageNum());
        pageMeta.put("pageSize", pageInfo.getPageSize());
        pageMeta.put("total", pageInfo.getTotal());
        pageMeta.put("pages", pageInfo.getPages());
        pageMeta.put("isFirstPage", pageInfo.isIsFirstPage());
        pageMeta.put("isLastPage", pageInfo.isIsLastPage());

        // 合并最终结果
        Map<String, Object> result = new HashMap<>();
        result.put("logs", logList);       // list of map
        result.put("pageInfo", pageMeta);  // map

        return DataMap.success().setData(result);
    }

}
