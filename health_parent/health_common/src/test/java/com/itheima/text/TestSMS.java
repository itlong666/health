package com.itheima.text;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.health.utils.SMSUtils;
import com.itheima.health.utils.ValidateCodeUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Author Mr.Liu
 * @Date 2020/1/9 20:57
 **/

public class TestSMS {
    @Test
    public void fun() throws ClientException {
        Integer integer = ValidateCodeUtils.generateValidateCode(6);
        System.out.println(integer);
        SMSUtils.sendShortMessage("17343209891", integer.toString());
    }

    @Test
    public void funs() throws ParseException {

        String s="2019-11";
        Date dates = new SimpleDateFormat("yyyy-MM").parse(s);
        String format = new SimpleDateFormat("yyyy-MM").format(dates);
        System.out.println(format);


    }
}
