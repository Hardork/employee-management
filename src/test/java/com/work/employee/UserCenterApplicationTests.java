package com.work.employee;

import org.springframework.boot.test.context.SpringBootTest;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@SpringBootTest
class UserCenterApplicationTests {





    /**
     * 测试统计用户访问量
     */


    public String getPreDateString() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date previousDay = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
        return simpleDateFormat.format(previousDay);
    }




}
