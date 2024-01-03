package com.work.employee.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:HWQ
 * @DateTime:2023/6/29 21:43
 * @Description:
 **/
public class DateFormatUtils {
    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
