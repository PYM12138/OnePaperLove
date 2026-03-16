package com.pym.mingblog.controller;

import com.github.pagehelper.PageHelper;
import com.pym.mingblog.model.Article;
import com.pym.mingblog.model.ArticleOperationLog;
import com.pym.mingblog.model.Tag;
import com.pym.mingblog.service.ArticleOperationLogService;
import com.pym.mingblog.service.ArticleService;
import com.pym.mingblog.service.ArticleTagService;
import com.pym.mingblog.utils.ArticleLogUtil;
import com.pym.mingblog.utils.DataMap;
import com.pym.mingblog.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Ming
 * @Date 2025/4/14 23:06
 * @Description: 文章功能业务层
 */
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleLogUtil articleLogUtil;

    @Autowired
    private ArticleTagService articleTagService;

    //保存文章
    @PostMapping("/saveArticles")
    public ResponseEntity<?> createOrUpdateArticle(@RequestBody Article article) {
        try {
            article.setIsDelete((byte) 0); // 默认不删除

            // 如果 article.getTags() 不为空，则提取其中的 tagName，否则返回一个空列表
            List<String> tagNames = article.getTags() != null
                    // 将 tags 列表转为 Stream 以进行后续处理
                    ? article.getTags().stream()
                    // 从每个 Tag 对象中提取 tagName 字段
                    .map(Tag::getTagName)
                    // 过滤掉值为 null 的 tagName，避免空值导致数据库出错
                    .filter(Objects::nonNull)
                    // 将处理后的 Stream 收集成 List<String>
                    .collect(Collectors.toList())
                    // 如果 tags 是 null，就返回一个空的 List，防止后续空指针异常
                    : Collections.emptyList();


            if (article.getArticleId() != null && article.getArticleId() > 0) {
                Long existingArticle = articleService.getArticleId(article.getArticleId());

                if (existingArticle != null) {
                    //更新文章
                    articleService.updateArticle(article);
                    // 保存标签
                    articleTagService.saveTagsForArticle(article.getArticleId(), tagNames);

                    articleLogUtil.log(article.getArticleId(), "Ming", "修改", "Ming修改了一篇文章,标题为:《" + article.getTitle() + "》");
                    return ResponseEntity.ok(JsonResult.success().toJSON());
                }
            }

            //新增文章
            articleService.addArticle(article);
            articleTagService.saveTagsForArticle(article.getArticleId(), tagNames); // 保存标签
            articleLogUtil.log(article.getArticleId(), "Ming", "发表", "Ming发表了一篇文章,标题为:《" + article.getTitle() + "》");
            return ResponseEntity.status(HttpStatus.CREATED).body(JsonResult.success().toJSON());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonResult.fail().toJSON());
        }
    }


    //查询所有文章
    @GetMapping(value = "/findAllArticles", produces = MediaType.APPLICATION_JSON_VALUE)
    public String showAllArticles(@RequestParam("pageNum") int pageNum,
                                  @RequestParam("pageSize") int pageSize){
        DataMap<?> allArticles = articleService.findAllArticles(pageSize, pageNum);
        return JsonResult.build(allArticles).toJSON();
    }

    //查询单篇文章
    @GetMapping(value = "/findArticleById")
    public String showArticle(@RequestParam("articleId") long articleId){
        /*DataMap<?> article = articleService.findArticleById(articleId);
        return JsonResult.build(article).toJSON();*/
        DataMap<?> article = articleService.findArticleById(articleId);
        return JsonResult.build(article).toJSON();
    }

    //删除文章
    @PostMapping("/article/delete")
    public String deleteArticle(@RequestParam Long articleId) {
        //需要先获取即将要删除的文章标题
        DataMap<?> articleById = articleService.findArticleById(articleId);
        HashMap<String, Object> data = (HashMap<String, Object>) articleById.getData();

        // 提取标题用于日志记录
        String title = (String) data.get("articleTitle");

        //文章删除记录
        boolean success = articleService.deleteArticle(articleId, 1) > 0;
        if (success) {
            articleLogUtil.log(articleId, "Ming", "删除", "Ming删除了一篇文章,标题为:《" + title + "》");
        }


        return JsonResult.success().toJSON();
    }

    @Value("${upload.path}")
    private String uploadBasePath;

    //上传文章中的图片
    @PostMapping("/uploadImage")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("editormd-image-file") MultipartFile file,
                                           HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        if (file.isEmpty()) {
            result.put("success", 0);
            result.put("message", "文件为空");
            return result;
        }

        try {
            // 上传目录（你可以根据需求修改路径）
            String uploadDir = uploadBasePath;  // 使用注入的路径
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 计算文件MD5
            String md5 = DigestUtils.md5Hex(file.getInputStream());

            // 在目录里查找是否已有相同MD5的文件（文件名以md5_开头）
            // 在指定目录 d==dir 中查找所有文件名以 md5 + "_" 开头的文件，
            // 用于判断是否已经上传过相同内容（MD5 值相同）的文件，避免重复保存
            File[] matchedFiles = dir.listFiles((d, name) -> name.startsWith(md5 + "_"));

            String fileName;
            if (matchedFiles != null && matchedFiles.length > 0) {
                // 找到重复文件，直接用第一个文件名
                fileName = matchedFiles[0].getName();
            } else {
                // 不存在重复文件，保存新文件
                String originalFilename = file.getOriginalFilename();
                fileName = md5 + "_" + originalFilename;
                File dest = new File(dir, fileName);
                file.transferTo(dest);
            }

            // 返回图片访问URL，确保你的静态资源映射正确
            String imageUrl = request.getContextPath() + "/uploads/" + fileName;

            result.put("success", 1);
            result.put("message", "上传成功");
            result.put("url", imageUrl);
            return result;

        } catch (Exception e) {
            result.put("success", 0);
            result.put("message", "上传失败：" + e.getMessage());
            return result;
        }
    }





    @PostMapping("/uploadImageArticle")
    public Map<String, Object> uploadCoverImage(@RequestParam("file") MultipartFile file,
                                                HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        if (file.isEmpty()) {
            result.put("success", false);
            result.put("message", "文件为空");
            return result;
        }

        try {
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();

            // 计算文件哈希值（MD5）
            String fileHash = DigestUtils.md5Hex(file.getInputStream());

            // 构造文件名：<哈希>_<原始文件名>
            String fileName = fileHash + "_" + originalFilename;

            // 保存路径
            String uploadDir = uploadBasePath + "articles/";  // 组合子目录
            File dir = new File(uploadDir);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new IOException("无法创建目录：" + uploadDir);
            }

            File dest = new File(uploadDir + fileName);

            // 如果文件不存在就保存
            if (!dest.exists()) {
                file.transferTo(dest);
            }

            // 返回图片 URL
            String imageUrl = request.getContextPath() + "/uploads/articles/" + fileName;

            result.put("success", true);
            result.put("url", imageUrl);
            return result;

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "上传失败：" + e.getMessage());
            return result;
        }
    }


}