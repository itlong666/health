package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import com.itheima.health.service.CheckItemService;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Mr.Liu
 * @Date 2020/1/4 12:05
 **/
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);

        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);

    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //PageHelper.startPage(currentPage,pageSize);
        // 响应分页插件的Page对象
        //Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        //return new PageResult(page.getTotal(),page.getResult());

        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page =checkGroupDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //设置检查组合和检查项的关联关系，向中间表中添加数据
    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                Map<String, Integer> map = new HashMap<String, Integer>();
                map.put("checkgroup_id", checkGroupId);
                map.put("checkitem_id", checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
            return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
        }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1：根据检查组id删除中间表数据（清理原有关联关系）
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //2：向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);
        //3：更新检查组基本信息
        checkGroupDao.edit(checkGroup);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

}

