package com.pym.mingblog.mapper;



import com.pym.mingblog.model.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * @Author: Ming
 * @Date 2025/5/20 16:10
 * @Description:
 */

@Mapper
public interface ArticleTagMapper {

    // 插入一条关联记录
    @Insert("INSERT INTO article_tags (articleId, tag_id) VALUES (#{articleId}, #{tagId})")
    void addArticleTag(@Param("articleId") Long articleId, @Param("tagId") Integer tagId);

    // 批量插入标签（可配合XML实现更高效）
    @Insert({
            "<script>",
            "INSERT INTO article_tags (articleId, tag_id) VALUES",
            "<foreach collection='tagIds' item='tagId' separator=','>",
            "(#{articleId}, #{tagId})",
            "</foreach>",
            "</script>"
    })
    void addArticleTagsBatch(@Param("articleId") Long articleId, @Param("tagIds") List<Integer> tagIds);

    // 删除某篇文章的所有标签
    @Delete("DELETE FROM article_tags WHERE articleId = #{articleId}")
    void deleteTagsByArticleId(@Param("articleId") Long articleId);

    // 查询某篇文章的所有标签
    @Select("SELECT t.tag_id, t.tagName " +
            "FROM tags t " +
            "JOIN article_tags at ON t.tag_id = at.tag_id " +
            "WHERE at.articleId = #{articleId}")
    List<Tag> getTagsByArticleId(@Param("articleId") Long articleId);
}
