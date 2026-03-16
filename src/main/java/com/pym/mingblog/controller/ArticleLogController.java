package com.pym.mingblog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pym.mingblog.mapper.ArticleOperationLogMapper;
import com.pym.mingblog.model.ArticleOperationLog;
import com.pym.mingblog.utils.ArticleLogUtil;
import com.pym.mingblog.utils.DataMap;
import com.pym.mingblog.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Ming
 * @Date 2025/4/26 13:24
 * @Description: 文章操作日志控制层
 */

@RestController
public class ArticleLogController {

    @Autowired
    private ArticleLogUtil articleLogUtil;

    /**
     * 查询操作日志（支持模糊搜索 + 分页）
     */
    @GetMapping("/logs/search")
    public String searchLogs(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5000") int pageSize,
            @RequestParam(required = false) String keyword
    ) {
        if (keyword == null || keyword.trim().isEmpty()) {
            DataMap<?> allLogs = articleLogUtil.findAllLogs(pageNum, pageSize);

            return JsonResult.build(allLogs).toJSON();
        } else {
            DataMap<?> searchLogs = articleLogUtil.searchLogs(keyword.trim(), pageNum, pageSize);
            return JsonResult.build(searchLogs).toJSON();
        }
    }
}

