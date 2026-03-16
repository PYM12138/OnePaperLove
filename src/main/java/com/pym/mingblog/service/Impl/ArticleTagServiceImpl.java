package com.pym.mingblog.service.Impl;
import com.pym.mingblog.mapper.ArticleTagMapper;
import com.pym.mingblog.mapper.TagMapper;
import com.pym.mingblog.model.Tag;
import com.pym.mingblog.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
/**
 * @Author: Ming
 * @Date 2025/5/20 16:14
 * @Description:
 */
@Service
public class ArticleTagServiceImpl implements ArticleTagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    @Transactional
    public void saveTagsForArticle(Long articleId, List<String> tagNames) {
        //如果前端传过来的是空标签列表，则直接执行删除标签关联，达到一个删除的目的
        if (tagNames == null || tagNames.isEmpty()) {
            // 没有标签则清空文章标签关联
            articleTagMapper.deleteTagsByArticleId(articleId);
            return;
        }

        // 去重 & 去除空字符串
        Set<String> distinctTagNames = tagNames.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());

        // 先查询所有已有标签
        List<Tag> allTags = tagMapper.getAllTags();

        // 构建 tagName -> Tag 映射，方便快速查找
        Map<String, Tag> tagMap = allTags.stream()
                .collect(Collectors.toMap(Tag::getTagName, t -> t));

        List<Integer> tagIdsToBind = new ArrayList<>();

        for (String tagName : distinctTagNames) {
            Tag tag = tagMap.get(tagName);
            if (tag == null) {
                // 标签不存在，新增标签
                Tag newTag = new Tag();
                newTag.setTagName(tagName);
                tagMapper.insertTag(newTag); // 记得在 TagMapper 写 insertTag 方法
                tagIdsToBind.add(newTag.getTagId());
                tagMap.put(tagName, newTag);
            } else {
                tagIdsToBind.add(tag.getTagId());
            }
        }

        // 先删除文章原有标签关联
        articleTagMapper.deleteTagsByArticleId(articleId);

        // 批量插入文章标签关联
        if (!tagIdsToBind.isEmpty()) {
            articleTagMapper.addArticleTagsBatch(articleId, tagIdsToBind);
        }
    }
}
