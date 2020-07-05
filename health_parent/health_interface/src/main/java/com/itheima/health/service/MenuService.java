package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface MenuService {
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    List<Map<String,Object>> getMenu(String username);
}
