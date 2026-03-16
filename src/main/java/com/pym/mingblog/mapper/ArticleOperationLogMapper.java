package com.pym.mingblog.mapper;

import com.pym.mingblog.model.ArticleOperationLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: Ming
 * @Date 2025/4/22 15:28
 * @Description: 文章操作描述
 */
@Mapper
public interface ArticleOperationLogMapper {


    @Select("SELECT * FROM article_operation_log " +
            "WHERE (user_name LIKE CONCAT('%', #{keyword}, '%') " +
            "OR description LIKE CONCAT('%', #{keyword}, '%')) " +
            "ORDER BY operation_time DESC")
    //模糊查询
    List<ArticleOperationLog> searchLogs(@Param("keyword") String keyword);



    @Select("SELECT * FROM article_operation_log ORDER BY operation_time DESC")
    List<ArticleOperationLog> findAll();

    @Insert("INSERT INTO article_operation_log (article_id,user_name,operation_type,operation_time,description) " +
            "VALUES(#{articleId},#{userName},#{operationType},#{operationTime},#{description}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertOne(ArticleOperationLog articleOperationLog);

}
