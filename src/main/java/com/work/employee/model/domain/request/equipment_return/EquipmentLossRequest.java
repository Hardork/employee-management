package com.work.employee.model.domain.request.equipment_return;

import lombok.Data;

@Data
public class EquipmentLossRequest{
    /**
     * 借用表id
     */
    private Long borrowID;

    /**
     * 归还数量
     */
    private Integer returnNum;

    /**
     * 借用器材编号
     */
    private String equipmentNo;

    /**
     * 借用人员学号或教工号
     */
    private String borrowPersonnelNo;

    /**
     * 器材名字
     */
    private String equipmentName;

    /**
     * 是否失踪
     */
    private int hasLoss;

    /**
     * 失踪数量
     */
    private int lossNum;


}
