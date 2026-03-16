package com.pym.mingblog.config;

import com.pym.mingblog.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: Ming
 * @Date 2025/3/26 21:39
 * @Description: Spring Security 配置
 * */


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

/* @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/


   @Bean
   public PasswordEncoder passwordEncoder() {
       return NoOpPasswordEncoder.getInstance();  // 这将跳过密码加密
   }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .csrf().disable() //忽略 CSRF 校验
                .headers().frameOptions().sameOrigin().and() // ✅ 安全地允许来自同源的 iframe 加载
                 .authorizeRequests()
                .antMatchers("/uploads/**", "/register", "/login", "/").permitAll()// 允许所有人访问
                .antMatchers("/uploadImage").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN") // 需要 ADMIN 角色
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/doLogin") .failureUrl("/login?error").defaultSuccessUrl("/admin/index",true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");// 退出成功跳转到登录页




    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }
}
