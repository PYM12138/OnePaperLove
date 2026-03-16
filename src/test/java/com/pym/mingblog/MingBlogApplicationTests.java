package com.pym.mingblog;

import com.pym.mingblog.mapper.ArticleMapper;
import com.pym.mingblog.mapper.ArticleOperationLogMapper;
import com.pym.mingblog.mapper.CategoryMapper;
import com.pym.mingblog.mapper.TagMapper;
import com.pym.mingblog.model.Article;
import com.pym.mingblog.model.ArticleOperationLog;
import com.pym.mingblog.model.Category;
import com.pym.mingblog.model.Tag;
import com.pym.mingblog.service.ArticleService;
import com.pym.mingblog.service.CategoryService;
import com.pym.mingblog.utils.DataMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@SpringBootTest
class MingBlogApplicationTests {
    @Autowired
    ArticleMapper articleMapper;
//    @Autowired
//    CategoryMapper categoryMapper;
    @Autowired
    ArticleService articleService;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    ArticleOperationLogMapper articleOperationLogMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CategoryService categoryService;

    @Test
    void contextLoads() {
      /*  LocalDateTime now = LocalDateTime.now();
        ArticleOperationLog articleOperationLog =  new ArticleOperationLog(0L, 1L, "admin", "delete", now, "delete article");
        articleOperationLogMapper.insertOne(articleOperationLog);*/
        // System.out.println(articleOperationLogMapper.findAll());
      /*  DataMap allCategories = categoryService.findAllCategories();
        System.out.println(allCategories);*/

     /*   Category c=new Category();
        c.setCategoryName("123");
        Integer integer = categoryMapper.countByCategoryName(c);
        System.out.println(integer);*/
/*
        List<Tag> tagsByArticleId = tagMapper.getTagsByArticleId(2023072601L);
        for (Tag tag : tagsByArticleId) {
            System.out.println(tag);
        }
*/

   /*     List<Article> allArticlesWithCategoryAndTags = articleMapper.getAllArticlesWithCategoryAndTags();
        for (Article article : allArticlesWithCategoryAndTags) {
            System.out.println(article);
        }*/
     /*   List<Article> 学习 = articleMapper.getArticlesByTagName("学习");
        for (Article article : 学习) {
            System.out.println(article);
        }*/
   /*     List<Category> 学习1 = categoryMapper.getCategoriesByTagName("学习");
        for  (Category category : 学习1) {
            System.out.println(category);
        }*/



    }


}





