package com.pym.mingblog.mapper;


import com.pym.mingblog.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: Ming
 * @Date 2025/3/26 21:41
 * @Description: 用户权限
 */
@Mapper
public interface UserMapper {

    // 根据用户名查询用户
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    // 插入新用户
    @Insert("INSERT INTO users (username, password, email, enabled) " +
            "VALUES (#{username}, #{password}, #{email}, #{enabled})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

    // 更新用户信息
    @Update("UPDATE users SET password = #{password}, email = #{email}, enabled = #{enabled} WHERE username = #{username}")
    int updateUser(User user);

    // 删除用户
    @Delete("DELETE FROM users WHERE username = #{username}")
    int deleteUser(@Param("username") String username);

    // 查询用户的权限
    @Select("SELECT authority FROM authorities WHERE username = #{username}")
    List<String> getAuthorities(@Param("username") String username);
}
