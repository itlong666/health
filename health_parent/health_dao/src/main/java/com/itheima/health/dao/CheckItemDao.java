package com.itheima.health.dao;

import com.itheima.health.pojo.CheckItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckItemDao {
    public void add(CheckItem checkItem);

    List<CheckItem> findPage(String queryString);

    void deleteById(Integer id);

    long findCountByCheckItemId(Integer id);

    CheckItem findCheckItemById(Integer id);

    void edit(CheckItem checkItem);

    List<CheckItem> findAll();

}
