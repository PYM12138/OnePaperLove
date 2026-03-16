package com.pym.mingblog.mapper;

import com.pym.mingblog.model.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Ming
 * @Date 2025/5/19 16:54
 * @Description: 分类
 */
@Mapper
public interface CategoryMapper {
    @Select("SELECT * FROM categories")
    List<Category> queryAllCategories();

    // 查询分类名是否存在
    @Select("SELECT COUNT(*) FROM categories WHERE category_name = #{categoryName}")
    Integer countByCategoryName(Category category);

    @Insert("INSERT INTO categories (category_name) VALUES (#{categoryName})")
    Integer insertCategory(Category category);


    @Select("SELECT DISTINCT c.category_id, c.category_name " +
            "FROM articles a " +
            "JOIN article_tags at ON a.articleId = at.articleId " +
            "JOIN tags t ON at.tag_id = t.tag_id " +
            "JOIN categories c ON a.category_id = c.category_id " +
            "WHERE t.tag_name = #{tagName} AND a.isDelete = 0")
    List<Category> getCategoriesByTagName(@Param("tagName") String tagName);


}
