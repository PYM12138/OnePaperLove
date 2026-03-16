package com.pym.mingblog.controller;

import com.pym.mingblog.mapper.ArticleMapper;
import com.pym.mingblog.model.Article;
import com.pym.mingblog.service.ArticleService;
import com.pym.mingblog.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author  Ming
 * @Date 2023/6/6 23:42
 * @Description 首页后台直链跳转
 */

@Controller
public class DirectLinkController {
    @Autowired
    ArticleService articleService;

    //直链跳转
    //前台
    @GetMapping("/")
    public String index() {
        return "index";
    }

    //文章详情页
    @GetMapping("/article/{articleId}")
    public String article(@PathVariable("articleId") long articleId,
                          HttpServletResponse response,
                          HttpServletRequest request, Model model) {
        //为了之后的markdown格式准备的
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //清除回跳的Url,接下里要写页面回跳，但是这个地方不用，所以清除
        request.getSession().removeAttribute("lastUrl");

        //这里需要把标题和部分内容带到页面上去，为之后能给百度抓取
        Map<String, String> map = articleService.getArticleTitleAndSummary(articleId);
        String articleTitle = map.get("articleTitle");
        if (articleTitle!=null){
            model.addAttribute("articleTitle",articleTitle);
            String articleSummary = map.get("articleSummary");
            if (articleSummary.length() <= 110) {//摘要不超过110个字符
                model.addAttribute("articleSummary", articleSummary);
            } else {
                model.addAttribute("articleSummary", articleSummary.substring(0, 110));
            }
        }
        //将文章id存入响应头,给article页面获取id用的
        response.setHeader("articleId",String.valueOf(articleId));

        return "article";
    }

    //归档页
    @GetMapping("/archive")
    public String archive() {
        return "archive";
    }

    //学习页
    @GetMapping("/learn")
    public String learn() {
        return "learn";
    }

    //更新日记
    @GetMapping("/updateRecord")
    public String updateRecord() {
        return "updateRecord";
    }

    //关于我
    @GetMapping("/aboutMe")
    public String aboutMe() {
        return "aboutMe";
    }

    //登录 注册 找回密码
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/recoverPassword")
    public String recoverPassword() {
        return "recoverPassword";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }


    //后台首页
    @GetMapping("/admin/index")
    public String adminIndex() {
        return "admin/index";
    }

    //文章编辑页
   /* @GetMapping("/admin/Edit")
    public String adminEdit() {
        return "admin/edit";
    }

    @GetMapping("/admin/edit")
    public String editPage(@RequestParam("articleId") Long articleId, Model model) {
        // 你可以根据 articleId 查询文章信息，塞进 model 中回显
        DataMap<?> result = articleService.findArticleById(articleId);
        model.addAttribute("article", result);
        return "admin/edit";  // 这个会映射到 templates/admin/edit.html
    }
*/

    @GetMapping("/admin/article/edit")
    public String editPage(@RequestParam(value = "articleId", required = false) Long articleId, Model model) {
        if (articleId != null) {
            // 查询文章信息
            DataMap<?> result = articleService.findArticleById(articleId);
            // 只传递 data（实际是 Article 对象）到前端，避免 HTML 模板访问困难
            model.addAttribute("article", result.getData());
        }
        return "admin/edit";
    }

    //文章管理页
    @GetMapping("/admin/article/mana")
    public String adminManagerArticle() {
        return "admin/managerArticle";
    }


    //文章操作记录
    @GetMapping("/admin/article/log")
    public String adminArticleLog() {
        return "admin/articleLog";
    }


    //用户管理页
    @GetMapping("/admin/user/mana")
    public String adminUser() {
        return "admin/managerUser";
    }


    //用户详情页
    @GetMapping("/admin/user/log")
    public String userPage() {
        return "admin/userPage";
    }

    //评论审核
    @GetMapping("/admin/comment/new")
    public String newMessage() {
        return "admin/newMessage";
    }

    //历史评论
    @GetMapping("/admin/comment/history")
    public String historyMessage() {
        return "admin/historyMessage";
    }


}
