package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.CheckGroupService;
import com.itheima.health.service.CheckItemService;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Mr.Liu
 * @Date 2020/1/6 12:47
 **/
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealDao setmealDao;

    @Autowired
    JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        // 1：新增套餐
        setmealDao.add(setmeal);
        // 2：向套餐和检查组的中间表中插入数据
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            //绑定套餐和检查组的多对多关系
            setSetmealAndCheckGroup(setmeal.getId(), checkgroupIds);
        }
        //将图片名称保存到Redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
    }

    //绑定套餐和检查组的多对多关系
    private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("setmeal_id", id);
            map.put("checkgroup_id", checkgroupId);
            setmealDao.setSetmealAndCheckGroup(map);
        }
    }

    @Override
    public PageResult QueryPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setmealDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(int id) {
        Setmeal setmeal = setmealDao.findById(id);
        List<CheckGroup> checkGroupList = setmealDao.findCheckGroupListById(setmeal.getId());
        if (checkGroupList != null && checkGroupList.size() > 0) {
            for (CheckGroup checkGroup : checkGroupList) {
                List<CheckItem> checkItem = setmealDao.findCheckItemListById(checkGroup.getId());
            checkGroup.setCheckItems(checkItem);
            }
        }
        setmeal.setCheckGroups(checkGroupList);
        return setmeal;
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    @Override
    public List<Map<String, Object>> findGenderCount() {
        return  setmealDao.findGenderCount();
    }

    @Override
    public List<Map<String, Object>> findAgeCount() {
        return setmealDao.findAgeCount();
    }
}
