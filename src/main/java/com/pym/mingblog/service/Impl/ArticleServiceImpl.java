package com.pym.mingblog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pym.mingblog.mapper.ArticleMapper;
import com.pym.mingblog.mapper.TagMapper;
import com.pym.mingblog.model.Article;
import com.pym.mingblog.model.Tag;
import com.pym.mingblog.service.ArticleService;
import com.pym.mingblog.utils.DataMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ming
 * @Date 2023/7/30 16:45
 * @Description: 文章的增删改查实现
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    TagMapper tagMapper;

    //查询全部文章并分页
    @Override
    public DataMap<?> findAllArticles(int rows, int pageNum) {
        PageHelper.startPage(pageNum,rows);
        List<Article> articles = articleMapper.getAllArticlesWithCategory();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        List<Map<String, Object>> newArticles = new ArrayList<>();
        Map<String, Object> map;
        for (Article article : articles) {
            if (article.getIsDelete() == 0){//0代表文章没有被删除
                map = new HashMap<>();
                map.put("articleTitle",article.getTitle());
                map.put("articleId",article.getArticleId());
                map.put("articleContent" , article.getContent());
                map.put("articleType" , article.getType());
                map.put("articlePublishDate" , article.getPublishDate());
                map.put("articleImageUrl" , article.getImageUrl());
                map.put("articleSummary" , article.getSummary());
                map.put("categoryName", article.getCategory().getCategoryName());

                //获取Tag标签
                List<Tag> tags = tagMapper.getTagsByArticleId(article.getArticleId());
                map.put("tags", tags);
                //映射好的Map放入list中
                newArticles.add(map);
            }

        }
        // 使用FastJSON进行转换
      //  JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(newArticles));

        //分页处理
        Map<String, Object> thisPageInfo = new HashMap<>();
        thisPageInfo.put("pageNum",pageInfo.getPageNum());
        thisPageInfo.put("pageSize",pageInfo.getPageSize());
        thisPageInfo.put("total",pageInfo.getTotal());
        thisPageInfo.put("pages",pageInfo.getPages());
        thisPageInfo.put("isFirstPage",pageInfo.isIsFirstPage());
        thisPageInfo.put("isLastPage",pageInfo.isIsLastPage());
      //  jsonArray.add(thisPageInfo);
        // 创建 DataMap<JSONArray> 对象并设置值
        //这是封装的正常流程，但是我们将常用的success方法和fail方法提取出来，就可以简略一下了
       /* DataMap<JSONArray> dataMap = new DataMap<>();
        dataMap.setData(jsonArray);
        dataMap.setCode(200);
        dataMap.setMessage("请求成功");
        dataMap.setIsSuccess(true);
         System.out.println(dataMap.toString());
        */
//        System.out.println(DataMap.issuccess().setData(jsonArray).toString());
        Map<String, Object> result = new HashMap<>();
        result.put("articles", newArticles);     // 是 List<Map>
        result.put("pageInfo", thisPageInfo);    // 是 Map<String, Object>

        return DataMap.success().setData(result);


       // return  DataMap.success().setData(jsonArray);
    }

    //文章ID查询文章
    @Override
    public DataMap<?> findArticleById(long articleId) {
        Article article = articleMapper.getArticleWithCategoryById(articleId);
        Map<String, Object> data ;
        if (article.getIsDelete()==0){
            data =new HashMap<>();
            data.put("articleTitle",article.getTitle());
            data.put("articleId",article.getArticleId());
            data.put("articleContent" , article.getContent());
            data.put("articleType" , article.getType());
            data.put("articlePublishDate" , article.getPublishDate());
            data.put("articleImageUrl" , article.getImageUrl());
            data.put("articleSummary" , article.getSummary());
            data.put("categoryId", article.getCategory().getCategoryId());   // ✅ 加上这一行
            data.put("categoryName", article.getCategory().getCategoryName());

            //标签
            List<Tag> tags = tagMapper.getTagsByArticleId(articleId);
            data.put("tags", tags);
        }else{
            return DataMap.fail();
        }

        return DataMap.success().setData(data);
    }

    //获取文章摘要和文章标题
    @Override
    public Map<String, String> getArticleTitleAndSummary(long articleId) {
        Article articleInfo = articleMapper.getArticleTitleAndSummary(articleId);
        Map<String, String> map=new HashMap<>();
        if (articleInfo.getIsDelete()==0) {
            map.put("articleTitle", articleInfo.getTitle());
            map.put("articleSummary", articleInfo.getSummary());
        }
        return map;
    }

    //添加文章
    @Override
    public void addArticle(Article article) {
        articleMapper.insertArticle(article);
    }

    @Override
    public Integer deleteArticle(Long articleId, Integer isDelete) {

        return articleMapper.updateArticleIsDelete(articleId, isDelete);
    }

    @Override
    public Integer updateArticle(Article article) {

        return articleMapper.updateArticle(article);
    }

    @Override
    public Long getArticleId(Long articleId) {
        return articleMapper.getArticleId(articleId);
    }


    @Override
    public List<Article> findAllArticlesWithCategoryAndTags() {
        return articleMapper.getAllArticlesWithCategoryAndTags();
    }

    @Override
    public List<Article> getArticlesByTagName(String tagName) {
        if (tagName != null) {
            return articleMapper.getArticlesByTagName(tagName);
        }
        return null;
    }


}
