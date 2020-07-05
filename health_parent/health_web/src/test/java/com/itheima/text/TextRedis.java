package com.itheima.text;

import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.utils.QiniuUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Mr.Liu
 * @Date 2020/1/6 19:30
 **/
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class TextRedis {

    @Autowired
    JedisPool jedisPool;

    // 删除Redis中2个不同key值存储的不同图片
    @Test
    public void test() {
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String pic = iterator.next();
            System.out.println("删除的图片：" + pic);
            // 删除七牛云上的图片
            QiniuUtils.deleteFileFromQiniu(pic);
            // 删除Redis中key值为SETMEAL_PIC_RESOURCE的数据
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, pic);
        }
    }

    @Test
    public void fun() {
     /*   Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);//获得当前日期之前12个月的日期

        List<String> list = new ArrayList<String>();
        for(int i=0;i<12;i++){
            calendar.add(Calendar.MONTH,1);
            list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }*/

        Calendar calendar = Calendar.getInstance();//得到一个Calendar的实例
        calendar.add(Calendar.YEAR,-1);
        for(int i=0;i<12;i++) {
            calendar.add(Calendar.MONTH, 1);
            String s = new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
            System.out.println(s);
        }
    }
}