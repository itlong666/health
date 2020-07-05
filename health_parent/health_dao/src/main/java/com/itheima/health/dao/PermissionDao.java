package com.itheima.health.dao;

import com.itheima.health.pojo.Permission;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface PermissionDao {
    Set<Permission> findPermissionsByRoleId(Integer roleId);
}
