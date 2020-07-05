package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(Map<String, Integer> map);

    Page<Setmeal> selectByCondition(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(int id);

    List<CheckGroup> findCheckGroupListById(Integer id);

    List<CheckItem> findCheckItemListById(Integer id);

    List<Map<String,Object>> findSetmealCount();

    List<Map<String,Object>> findGenderCount();

    List<Map<String,Object>> findAgeCount();

}
