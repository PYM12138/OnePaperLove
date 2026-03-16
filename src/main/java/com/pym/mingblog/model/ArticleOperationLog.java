package com.pym.mingblog.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Ming
 * @Date 2025/4/22 15:23
 * @Description: 文章操作描述类
 */
@Data
public class ArticleOperationLog {
    private Long id;
    private Long articleId;
    private String userName;
    private String operationType;
    private LocalDateTime operationTime;
    private String description;

}

