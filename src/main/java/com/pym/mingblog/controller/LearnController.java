package com.pym.mingblog.controller;

import com.pym.mingblog.model.Article;
import com.pym.mingblog.model.Category;
import com.pym.mingblog.service.ArticleService;
import com.pym.mingblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Ming
 * @Date 2023/9/17 21:55
 * @Description: 学习Controller
 */
@RestController
@RequestMapping("/learn")
public class LearnController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取包含某标签的所有文章
     */
    @GetMapping("/articles/by-tag")
    public List<Article> getArticlesByTagName(@RequestParam String tag) {
        return articleService.getArticlesByTagName(tag);
    }

    /**
     * 获取包含某标签的文章所涉及的分类（去重）
     */
    @GetMapping("/categories/by-tag")
    public List<Category> getCategoriesByTagName(@RequestParam String tag) {
        return categoryService.getCategoriesByTagName(tag);
    }




}
