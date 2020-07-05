package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MenuDao {
    Page<Menu> findPage(String queryString);

    List<Integer> getMasterMenuId(String username);

    Map<String,Object> getMasterMenu(Integer masterId);

    List<Map<String,Object>> getChileMenu(Integer masterId);
}
