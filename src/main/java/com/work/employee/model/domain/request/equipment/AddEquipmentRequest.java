package com.work.employee.model.domain.request.equipment;

import lombok.Data;

import java.util.Date;

/**
 * @Author:HWQ
 * @DateTime:2023/9/13 13:39
 * @Description:
 **/
@Data
public class AddEquipmentRequest {
    /**
     * 器材编号
     */
    private String equipmentNo;

    /**
     * 器材名字
     */
    private String equipmentName;

    /**
     * 器材型号
     */
    private String equipmentModel;

    /**
     * 器材价格
     */
    private Double equipmentPrice;

    /**
     * 器材库存
     */
    private Integer leftNum;

    /**
     * 购买日期
     */
    private Date purchaseDate;
}
