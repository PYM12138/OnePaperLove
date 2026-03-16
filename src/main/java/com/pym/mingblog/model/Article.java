package com.pym.mingblog.model;

import lombok.Data;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @Author: Ming
 * @Date 2023/7/28 0:38
 * @Description: 文章
 */
@Data
public class Article {

    private Integer id;
    //文章ID
    private Long articleId;

    private String author;
    //文章原作者
    private String originalAuthor;

    private String title;

    private String content;
    //文章类型：原创 转载 分享
    private String type;

    private String publishDate;

    private String lastUpdateDate;
    //封面图片
    private String coverImageUrl;
    //图片地址
    private String imageUrl;
    //文章摘要
    private String summary;
    //点赞数
    private Integer likes = 0;
    //文章上一篇ID
    private Long lastArticleId;
    //文章下一篇ID
    private Long nextArticleId;
    //是否删除 0为正常 1为删除
    private Byte isDelete;
    //文章分类
    private Category category;
    //文章标签
    private List<Tag> tags;

/*    public Article(Long articleId, String title, String type, String publishDate, Byte isDelete,  Integer categoryId, String categoryName) {
        this.articleId = articleId;
        this.title = title;
        this.type = type;
        this.publishDate = publishDate;
        this.isDelete = isDelete;
        // 创建并设置 Category 对象
        this.category = new Category();
        this.category.setCategoryId(categoryId);
        this.category.setCategoryName(categoryName);
    }*/


}
