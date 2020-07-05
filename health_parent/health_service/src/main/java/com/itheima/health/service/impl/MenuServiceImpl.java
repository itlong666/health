package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Mr.Liu
 * @Date 2020/1/17 17:27
 **/
@Transactional
@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        List<Menu> menuList = menuDao.findPage(queryString);
        PageInfo<Menu> pageInfo = new PageInfo<>(menuList);
        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public List<Map<String, Object>> getMenu(String username) {
        List<Map<String, Object>> mapList = new ArrayList<>();

        List<Integer> masterListId = menuDao.getMasterMenuId(username);

        for (Integer integer : masterListId) {
            Map<String, Object> map = menuDao.getMasterMenu(integer);

            List<Map<String, Object>> chileMenuList = menuDao.getChileMenu(integer);

            map.put("children",chileMenuList);
            mapList.add(map);
        }
        return mapList;
    }

}
