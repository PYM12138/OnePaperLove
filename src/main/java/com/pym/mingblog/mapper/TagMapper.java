package com.pym.mingblog.mapper;

import com.pym.mingblog.model.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ming
 * @Date 2025/4/19 15:25
 * @Description: 标签
 */
@Mapper
public interface TagMapper {
    //根据文章ID查询标签
    @Select("SELECT t.* " +
            "FROM tags t " +
            "JOIN article_tags at ON t.tag_id = at.tag_id " +
            "WHERE at.articleId = #{articleId}")
    List<Tag> getTagsByArticleId(long articleId);

    //查询所有标签
    @Select("SELECT * FROM tags")
    List<Tag> getAllTags();

    //查询所有关联的文章ID和标签
    @Select("SELECT at.articleId, t.tag_name " +
            "FROM article_tags at " +
            "JOIN tags t ON at.tag_id = t.tag_id " +
            "ORDER BY at.articleId")
    List<Map<String, Object>> getAllArticleIdAndTagName();


    @Select("SELECT * FROM tags WHERE tag_name = #{tagName} LIMIT 1")
    Tag getTagByName(String tagName);

    @Insert("INSERT INTO tags(tag_name) VALUES(#{tagName})")
    @Options(useGeneratedKeys = true, keyProperty = "tagId",keyColumn = "tag_id")
    void insertTag(Tag tag);



}
