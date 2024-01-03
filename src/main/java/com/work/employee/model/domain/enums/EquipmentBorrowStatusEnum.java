package com.work.employee.model.domain.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:HCJ
 * @DateTime:2023/9/12
 * @Description:
 **/
public enum EquipmentBorrowStatusEnum {

    UNAPPROVED("未批",0),
    FAILED("未通过",1),
    BORROW("借用",2),
    APPROVED("归还审批",3),
    CONFIRM("确认",4);


    private final String text;

    private final Integer value;

    EquipmentBorrowStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static EquipmentBorrowStatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (EquipmentBorrowStatusEnum anEnum : EquipmentBorrowStatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
