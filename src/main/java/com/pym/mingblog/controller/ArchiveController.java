package com.pym.mingblog.controller;

import com.pym.mingblog.model.Article;
import com.pym.mingblog.service.ArticleService;
import com.pym.mingblog.service.CategoryService;
import com.pym.mingblog.utils.DataMap;
import com.pym.mingblog.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ming
 * @Date 2023/8/21 0:27
 * @Description:
 */
@RestController
@RequestMapping("/archive")
public class ArchiveController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ArticleService articleService;

    @GetMapping("/showArchive")
    public String showArchive(@RequestParam(value = "categoryName",defaultValue = "*") String categoryName){

        DataMap articlesByCategoryName = categoryService.sortArticleByCategoryName(categoryName);

        return JsonResult.build(articlesByCategoryName).toJSON();
    }
    @GetMapping("/group")
    public Map<String, List<Article>> archiveGroup(@RequestParam String mode) {

        List<Article> articles = articleService.findAllArticlesWithCategoryAndTags();

        if ("category".equals(mode)) {
            return categoryService.groupByCategory(articles);
        } else if ("month".equals(mode)) {
            return categoryService.groupByMonth(articles);
        } else {
            throw new IllegalArgumentException("未知模式：" + mode);
        }
    }







}
