package com.work.employee.utils;

import java.util.regex.Pattern;

/**
 * @Author:HWQ
 * @DateTime:2023/6/30 15:59
 * @Description:
 **/
public class StrUtils {
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if ((phoneNumber != null) && (!phoneNumber.isEmpty())) {
            return Pattern.matches("^1[3-9]\\d{9}$", phoneNumber);
        }
        return false;
    }
}
