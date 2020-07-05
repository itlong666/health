package com.itheima.health.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @Author Mr.Liu
 * @Date 2020/1/8 20:09
 **/
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference//(check = false)
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    //获取所有套餐信息
    @RequestMapping("/getSetmeal")
    public Result getSetmeal() {
        Jedis jedis = jedisPool.getResource();
        String str = jedis.get(RedisConstant.SETMEAL_LIST);
        try {
            if (str == null || "".equals(str)) {
                List<Setmeal> list = setmealService.findAll();
                str = new ObjectMapper().writeValueAsString(list);
                jedis.set(RedisConstant.SETMEAL_LIST, str);
            }
            List<Setmeal> list = new ObjectMapper().readValue(str, new TypeReference<List<Setmeal>>() {
            });
            jedis.close();
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    //根据id查询套餐信息
    @RequestMapping("/findById")
    public Result findById(int id) {
        Jedis jedis = jedisPool.getResource();
        String str = jedis.get(String.valueOf(id));
        try {
            if (str == null || "".equals(str)) {
                Setmeal setmeal = setmealService.findById(id);
                str = new Gson().toJson(setmeal);
                jedis.set(String.valueOf(id),str);
            }
            Setmeal setmeal = new Gson().fromJson(str, Setmeal.class);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
