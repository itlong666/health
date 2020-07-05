package com.itheima.health.job;

import com.itheima.health.dao.OrderDao;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author Mr.Liu
 * @Date 2020/1/22 11:54
 **/

public class ClearData {
    @Autowired
    private OrderDao orderDao;

    public void clearDatas() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,0);
        Date time = calendar.getTime();
        System.out.println(time);
        //orderDao.clearData(time);
    }
}
