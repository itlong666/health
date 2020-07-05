package com.itheima.health.dao;

import com.itheima.health.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface RoleDao {
    Set<Role> findRolesByUserId(Integer userId);

}
