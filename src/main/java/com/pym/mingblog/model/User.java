package com.pym.mingblog.model;

/**
 * @Author: Ming
 * @Date 2025/3/26 21:45
 * @Description:
 */

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean enabled; // TINYINT(1) 转换为 Boolean
 /*   private Long id;
    private String username;
    private String password;
    private String email;
    private Integer enabled;
    private String role; // 角色（ROLE_USER, ROLE_ADMIN）*/


  /*  public User(String username, String password, String email,Integer enabled,String role) {
        this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password); // 密码加密
        this.email=email;
        this.enabled=enabled;
        this.role = role;
    }


    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }
*/

}
