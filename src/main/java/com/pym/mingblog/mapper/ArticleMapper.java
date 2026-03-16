package com.pym.mingblog.mapper;

import com.pym.mingblog.model.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * @Author: Ming
 * @Date 2023/7/29 16:59
 * @Description: 文章Mapper
 */
@Mapper
public interface ArticleMapper {


    @Select("SELECT * FROM articles JOIN categories c ON c.category_id = articles.category_id WHERE isDelete = 0 ORDER BY publishDate DESC")
    @Result(property = "category.categoryId", column = "category_id")
    @Result(property = "category.categoryName", column = "category_name")
    List<Article> getAllArticlesWithCategory();


/*
//这个写法太浪费了
    @Select("SELECT a.*,  t.*, ac.* " +
            "FROM articles a " +
            "LEFT JOIN article_tags at ON a.id = at.article_id " +
            "LEFT JOIN tags t ON at.tag_id = t.tag_id " +
            "LEFT JOIN categories ac ON a.category_id = ac.category_id")
    @Results({
            @Result(property = "category.categoryId", column = "category_id"),
            @Result(property = "category.categoryName", column = "categoryName"),
            @Result(property = "category", column = "category_id", javaType = Category.class, one = @One(select = "getArticleCategoryById")),
            @Result(property = "tags", column = "tag_id", javaType = List.class, many = @Many(select = "getTagsByArticleId"))
    })
    List<Article> getAllArticlesWithTagsAndCategory();

    @Select("SELECT c.* " +
            "FROM categories c " +
            "JOIN articles a ON c.category_id = a.category_id " +
            "WHERE a.articleId = #{articleId}")
    Category getArticleCategoryById(int articleId);

    */

    @Select("select * from articles join categories c on c.category_id = articles.category_id WHERE articleId=#{articleId} AND isDelete=0")
    @Result(property = "category.categoryId", column = "category_id")
    @Result(property = "category.categoryName", column = "category_name")
    Article getArticleWithCategoryById(long articleId);


    @Select("select title,summary,isDelete from articles where articleId=#{articleId} AND isDelete=0")
    Article getArticleTitleAndSummary(long articleId);


   /*     @Select("SELECT a.articleId, a.title, a.publishDate, a.type, c.category_id, c.category_name " +
                "FROM articles a " +
                "JOIN categories c ON c.category_id = a.category_id " +
                "WHERE a.isDelete = 0 " +
                "ORDER BY a.publishDate DESC")
        @Results({
                @Result(property = "articleId", column = "articleId"),
                @Result(property = "title", column = "title"),
                @Result(property = "publishDate", column = "publishDate"),
                @Result(property = "type", column = "type"),
                @Result(property = "category.categoryId", column = "category_id"),
                @Result(property = "category.categoryName", column = "category_name"),
                @Result(property = "tags", column = "articleId",
                        many = @Many(select = "com.pym.mingblog.mapper.TagMapper.getTagsByArticleId"))
        })
        List<Article> getAllArticlesWithCategoryAndTags();*/
    @Select("SELECT a.articleId, a.title, a.publishDate, a.type, c.category_id, c.category_name " +
            "FROM articles a " +
            "JOIN categories c ON c.category_id = a.category_id " +
            "WHERE a.isDelete = 0 " +
            "ORDER BY a.publishDate DESC")
    @Results({
            @Result(property = "articleId", column = "articleId"),
            // 嵌套 Category 对象的映射（只有这部分需要写）
            @Result(property = "category.categoryId", column = "category_id"),
            @Result(property = "category.categoryName", column = "category_name"),

            // 嵌套标签集合 tags 的映射
            @Result(property = "tags", column = "articleId",
                    many = @Many(select = "com.pym.mingblog.mapper.TagMapper.getTagsByArticleId"))
    })
    List<Article> getAllArticlesWithCategoryAndTags();


    @Insert("INSERT INTO articles (" +
            "articleId, author, originalAuthor, title, content, type, " +
            "publishDate, lastUpdateDate,coverImageUrl, imageUrl, summary, likes, " +
            "category_id, isDelete" +
            ") VALUES (" +
            "#{articleId}, #{author}, #{originalAuthor}, #{title}, #{content}, #{type}, " +
            "#{publishDate}, #{lastUpdateDate},#{coverImageUrl}, #{imageUrl}, #{summary}, #{likes}, " +
            "#{category.categoryId}, #{isDelete}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertArticle(Article article);

    @Update("UPDATE articles SET " +
            "isDelete = #{isDelete} " +
            "WHERE articleId = #{articleId}")
    Integer updateArticleIsDelete(@Param("articleId") Long articleId, @Param("isDelete") Integer isDelete);

    @Update("UPDATE articles SET " +
            "author = #{author}, " +
            "originalAuthor = #{originalAuthor}, " +
            "title = #{title}, " +
            "content = #{content}, " +
            "type = #{type}, " +
            "publishDate = #{publishDate}, " +
            "lastUpdateDate = #{lastUpdateDate}, " +
            "coverImageUrl = #{coverImageUrl}, " +
            "imageUrl = #{imageUrl}, " +
            "summary = #{summary}, " +
            "likes = #{likes}, " +
            "category_id = #{category.categoryId}, " +
            "isDelete = #{isDelete} " +
            "WHERE articleId = #{articleId}")
    Integer updateArticle(Article article);

    //查询id是否存在
    @Select("SELECT articleId FROM articles WHERE articleId = #{articleId} ")
    Long getArticleId(Long articleId);


    @Select("SELECT a.articleId, a.author, a.title, a.content,a.imageUrl," +
            "a.type, a.publishDate, a.summary, a.likes, " +
            "c.category_id AS category_id, " +
            "c.category_name AS category_name " +
            "FROM articles a " +
            "JOIN article_tags at ON a.articleId = at.articleId " +
            "JOIN tags t ON at.tag_id = t.tag_id " +
            "JOIN categories c ON a.category_id = c.category_id " +
            "WHERE t.tag_name = #{tagName} AND a.isDelete = 0")
    @Results(id = "articleMap", value = {
            @Result(property = "articleId", column = "articleId"),
            @Result(property = "category.categoryId", column = "category_id"),
            @Result(property = "category.categoryName", column = "category_name"),
            @Result(property = "tags", column = "articleId",
                    many = @Many(select = "com.pym.mingblog.mapper.TagMapper.getTagsByArticleId"))
    })
    List<Article> getArticlesByTagName(@Param("tagName") String tagName);





}
