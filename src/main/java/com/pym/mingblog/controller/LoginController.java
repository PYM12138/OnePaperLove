package com.pym.mingblog.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Ming
 * @Date 2025/3/31 20:23
 * @Description:
 */

//@Controller
public class LoginController {

   // @GetMapping("/login")
    public String loginPage(Model model) {
        return "login"; // 返回 Thymeleaf 模板 login.html
    }
}
