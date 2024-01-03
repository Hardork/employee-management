package com.work.employee.model.domain.vo.equipment_borrow;

import lombok.Data;

import java.util.Date;

/**
 * @Author:HCJ
 * @DateTime:2023/9/13
 * @Description:
 **/
@Data
public class EquipmentBorrowVo {
    /**
     *
     */
    private Long borrowID;

    /**
     * 借用器材编号
     */
    private String equipmentNo;


    /**
     * 器材名字
     */
    private String equipmentName;

    /**
     * 借用人员学号或教工号
     */
    private String borrowPersonnelNo;

    /**
     * 正在借用数量
     */
    private Integer leftNum;


    /**
     * 状态 0未批 1未通过 2借用中 3确认
     */
    private Integer status;

    /**
     * 未通过原因
     */
    private String reason;

    /**
     * 借用日期
     */
    private Date borrowDate;

    /**
     * 规定归还日期
     */
    private Date specifiedDate;

}
