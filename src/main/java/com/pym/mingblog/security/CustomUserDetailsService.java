package com.pym.mingblog.security;

import com.pym.mingblog.mapper.UserMapper;
import com.pym.mingblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Ming
 * @Date 2025/3/26 21:44
 * @Description: 从数据源加载指定用户名的用户验证信息
 * 此方法被Spring Security框架调用以执行认证过程。
 * 它通过给定的用户名查找对应的用户，并构建一个包含用户详情的对象。
 * */


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @param username 用户尝试登录时提供的用户名
     * @return 包含用户验证信息的UserDetails对象
     * @throws UsernameNotFoundException 如果没有找到与给定用户名匹配的用户
     * 
     * 注意：此实现目前只分配了"ROLE_ADMIN"角色给所有用户。
     * 在未来版本中应根据实际业务需求扩展角色管理功能。
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询数据库，获取用户信息
        User user = userMapper.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户未找到: " + username);
        }

        // 从数据库加载角色列表
        List<String> roles = userMapper.getAuthorities(username);

        /**
         * 将用户的角色信息转换为Spring Security所需的GrantedAuthority类型
         * 目前系统设计为所有用户都具有管理员权限
         * 后续可根据需求增加多角色支持
         */
//        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));

        // 将角色转换为 GrantedAuthority 列表
        List<GrantedAuthority> authorities = roles.stream()      // 将角色字符串列表转为 Stream 流
                .map(SimpleGrantedAuthority::new)                // 将每个角色字符串映射为 SimpleGrantedAuthority 对象
                .collect(Collectors.toList());                   // 收集成一个 List<GrantedAuthority>，供 Spring Security 使用


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),                                      // 用户名
                user.getPassword(),                                      // 加密后的密码
                user.getEnabled() != null && user.getEnabled(),          // 账户是否启用
                true,                                                    // 账户是否未过期
                true,                                                    // 凭证是否未过期
                true,                                                    // 账户是否未锁定
                authorities                                               // 用户权限列表
        );


     /*   return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true, true, true, true, // 所有布尔参数代表账户状态标志，默认设为true
                authorities
        );*/
    }
}
