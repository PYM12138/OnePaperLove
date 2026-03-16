package com.pym.mingblog.controller;

import com.alibaba.fastjson.JSON;
import com.pym.mingblog.mapper.ArticleMapper;
import com.pym.mingblog.service.ArticleService;
import com.pym.mingblog.utils.DataMap;
import com.pym.mingblog.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: Ming
 * @Date 2023/7/31 0:05
 * @Description: 首页
 */
@RestController
public class IndexController {
    @Autowired
    ArticleService articleService;

/*    @GetMapping(value = "/findAllArticles", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showAllArticles(){
        DataMap allArticles = articleService.findAllArticles(10, 1);
        return JsonResult.build(allArticles).toJSON();
    }

    @GetMapping(value = "/findArticleById")
    public String showArticle(@RequestParam("articleId") long articleId){
        DataMap article = articleService.findArticleById(articleId);
        return JsonResult.build(article).toJSON();
    }*/

  /*  @PostMapping("/api/car/upload")
    public String uploadCar(@RequestBody byte[] bytes) throws IOException {
        File uploadDir = new File("./uploads/");
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String filename = "car_" + System.currentTimeMillis() + ".jpg";
        File dest = new File(uploadDir, filename);
        Files.write(dest.toPath(), bytes);

        return "✅ 上传成功: " + dest.getAbsolutePath();
    }*/







}
