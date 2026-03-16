package com.pym.mingblog.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pym.mingblog.mapper.ArticleMapper;
import com.pym.mingblog.mapper.CategoryMapper;
import com.pym.mingblog.mapper.TagMapper;
import com.pym.mingblog.model.Article;
import com.pym.mingblog.model.Category;
import com.pym.mingblog.model.Tag;
import com.pym.mingblog.service.CategoryService;
import com.pym.mingblog.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Ming
 * @Date 2023/8/10 0:08
 * @Description: 分类相关功能实现
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    CategoryMapper categoryMapper;

    //分类归类查询文章
    @Override
    public DataMap sortArticleByCategoryName(String categoryName) {
        //一页显示十条数据
        PageHelper.startPage(1, 10);
        List<Article> articles = articleMapper.getAllArticlesWithCategory();
     /*   if (categoryName.equals("*")){
            //全部查询
            articles = articleMapper.getAllArticlesWithCategory();
        }*/

        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        List<Map<String, Object>> newArticles = new ArrayList<>();
        Map<String, Object> map;
        for (Article article : articles) {
            if (article.getIsDelete() == 0) {//0代表文章没有被删除
                boolean containsStudyTag = false; // 初始化一个标志变量用于检查是否包含 "学习" 标签
                // 获取文章的标签
                List<Tag> tags = tagMapper.getTagsByArticleId(article.getArticleId());
                // 检查标签列表是否包含 "学习"
                for (Tag tag : tags) {
                    if ("学习".equals(tag.getTagName())) { // 如果包含 "学习" 标签
                        containsStudyTag = true; // 将标志变量设置为 true
                        break; // 不需要继续检查其他标签，可以退出循环
                    }
                }

                if (!containsStudyTag) { // 如果标签列表不包含 "学习" 标签
                    map = new HashMap<>();
                    map.put("articleTitle", article.getTitle());
                    map.put("articleId", article.getArticleId());
                    map.put("articleType", article.getType());
                    map.put("articlePublishDate", article.getPublishDate());
                    map.put("categoryName", article.getCategory().getCategoryName());

                    // 获取Tag标签
                    map.put("tags", tags);

                    // 映射好的Map放入list中
                    newArticles.add(map);
                }
            }
        }
        // 使用FastJSON进行转换
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(newArticles));
        //分页处理
        Map<String, Object> thisPageInfo = new HashMap<>();
        thisPageInfo.put("pageNum", pageInfo.getPageNum());
        thisPageInfo.put("pageSize", pageInfo.getPageSize());
        thisPageInfo.put("total", pageInfo.getTotal());
        thisPageInfo.put("pages", pageInfo.getPages());
        thisPageInfo.put("isFirstPage", pageInfo.isIsFirstPage());
        thisPageInfo.put("isLastPage", pageInfo.isIsLastPage());
        jsonArray.add(thisPageInfo);

        return DataMap.success().setData(jsonArray);
    }

    @Override
    public DataMap sortArticleByCategoryNameForLearn(String categoryName) {
        //一页显示十条数据
        PageHelper.startPage(1, 10);
        List<Article> articles = articleMapper.getAllArticlesWithCategory();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        List<Map<String, Object>> newArticles = new ArrayList<>();
        Map<String, Object> map;
        for (Article article : articles) {
            if (article.getIsDelete() == 0) {//0代表文章没有被删除
                boolean containsStudyTag = false; // 初始化一个标志变量用于检查是否包含 "学习" 标签
                // 获取文章的标签
                List<Tag> tags = tagMapper.getTagsByArticleId(article.getArticleId());
                // 检查标签列表是否包含 "学习"
                for (Tag tag : tags) {
                    if ("学习".equals(tag.getTagName())) { // 如果包含 "学习" 标签
                        containsStudyTag = true; // 将标志变量设置为 true
                        break; // 不需要继续检查其他标签，可以退出循环
                    }
                }

                if (containsStudyTag) { // 如果标签列表不包含 "学习" 标签
                    map = new HashMap<>();
                    map.put("articleTitle", article.getTitle());
                    map.put("articleId", article.getArticleId());
                    map.put("articleType", article.getType());
                    map.put("articlePublishDate", article.getPublishDate());
                    map.put("categoryName", article.getCategory().getCategoryName());

                    // 获取Tag标签
                    map.put("tags", tags);

                    // 映射好的Map放入list中
                    newArticles.add(map);
                }
            }
        }
        // 使用FastJSON进行转换
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(newArticles));
        //分页处理
        Map<String, Object> thisPageInfo = new HashMap<>();
        thisPageInfo.put("pageNum", pageInfo.getPageNum());
        thisPageInfo.put("pageSize", pageInfo.getPageSize());
        thisPageInfo.put("total", pageInfo.getTotal());
        thisPageInfo.put("pages", pageInfo.getPages());
        thisPageInfo.put("isFirstPage", pageInfo.isIsFirstPage());
        thisPageInfo.put("isLastPage", pageInfo.isIsLastPage());
        jsonArray.add(thisPageInfo);

        return DataMap.success().setData(jsonArray);

    }

    @Override
    public DataMap findAllCategories() {
        List<Category> allCategories = categoryMapper.queryAllCategories();
        if (allCategories.size() > 0) {
            return DataMap.success().setData(allCategories);
        }
        return null;
    }


    @Override
    public Integer addCategory(Category category) {
        int count = categoryMapper.countByCategoryName(category);
        if (count > 0) {
            return null;
        }
        return categoryMapper.insertCategory(category);
    }


    @Override
    public Map<String, List<Article>> groupByCategory(List<Article> articles) {
        return articles.stream()
                .filter(article -> article.getCategory() != null && article.getCategory().getCategoryName() != null)
                .collect(Collectors.groupingBy(article -> article.getCategory().getCategoryName()));
    }


    public Map<String, List<Article>> groupByMonth(List<Article> articles) {
        DateTimeFormatter inputFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm")
                .optionalStart()
                .appendPattern(":ss")
                .optionalEnd()
                .toFormatter();

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

        return articles.stream()
                .filter(a -> a.getPublishDate() != null && !a.getPublishDate().isEmpty())
                .collect(Collectors.groupingBy(a -> {
                    try {
                        return LocalDateTime.parse(a.getPublishDate(), inputFormatter).format(outputFormatter);
                    } catch (DateTimeParseException e) {
                        // 如果格式不对就分到“未知时间”组
                        return "未知时间";
                    }
                }));
    }

    @Override
    public List<Category> getCategoriesByTagName(String tagName) {
        if (tagName != null && !tagName.isEmpty()) {
            return categoryMapper.getCategoriesByTagName(tagName);
        }
        return null;
    }


}
