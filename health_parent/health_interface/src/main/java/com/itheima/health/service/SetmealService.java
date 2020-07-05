package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult QueryPage(Integer currentPage, Integer pageSize, String queryString);

    List<Setmeal> findAll();

    Setmeal findById(int id);

    List<Map<String,Object>> findSetmealCount();

    List<Map<String,Object>> findGenderCount();

    List<Map<String,Object>> findAgeCount();

}
