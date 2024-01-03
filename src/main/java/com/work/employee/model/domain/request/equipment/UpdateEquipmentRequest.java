package com.work.employee.model.domain.request.equipment;

import lombok.Data;

import java.util.Date;

/**
 * 器材表
 * @TableName equipment
 */
@Data
public class UpdateEquipmentRequest {

    /**
     * 器材id
     */
    private Long equipmentID;

    /**
     * 添加器材人员id
     */
    private String personnelNo;

    /**
     * 借用人员学号或教工号
     */
    private String borrowPersonnelNo;

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

    /**
     * 是否可用 0可用 1不可用
     */
    private Integer status;



}