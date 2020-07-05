package com.itheima.security;

import com.itheima.health.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Mr.Liu
 * @Date 2020/1/11 18:53
 **/
@Component
public class UserService implements UserDetailsService {
    public static BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
    //模拟数据库中的用户数据
    public static Map<String, User> map = new HashMap<String, User>();

    static {
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(encoder.encode("admin"));

        User user2 = new User();
        user2.setUsername("zhangsan");
        user2.setPassword("123");

        map.put(user1.getUsername(), user1);
        map.put(user2.getUsername(), user2);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username:" + username);
        User userInDb = map.get(username);//模拟根据用户名查询数据库
        if (userInDb == null) {
            //根据用户名没有查询到用户，抛出异常，表示登录名输入有误
            return null;
        }

        //模拟数据库中的密码，后期需要查询数据库
        //String passwordInDb = "{noop}" + userInDb.getPassword();

        String passwordInDb = userInDb.getPassword();

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        //授权，后期需要改为查询数据库动态获得用户拥有的权限和角色
        list.add(new SimpleGrantedAuthority("add")); // 权限
        list.add(new SimpleGrantedAuthority("delete")); // 权限
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 角色
        //返回User，参数一：存放登录名，参数二：存放数据库查询的密码（数据库获取的密码，默认会和页面获取的密码进行比对，成功跳转到成功页面，失败回到登录页面，并抛出异常表示失败），存放当前用户具有的角色
       return new org.springframework.security.core.userdetails.User(username, passwordInDb, list);
    }
}
