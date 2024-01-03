package com.work.employee.model.domain.vo.equipment_return;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 器材归还申请表
 * @TableName equipment_return
 */
@Data
public class EquipmentReturnVo implements Serializable {
    /**
     * 
     */
    private Long returnID;

    /**
     * 借用表id
     */
    private Long borrowID;

    /**
     * 器材编号
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
     * 归还时间
     */
    private Date returnDate;

    /**
     * 归还数量
     */
    private Integer returnNum;

    /**
     * 状态 0待审批 1通过 2未通过
     */
    private Integer status;

    /**
     * 罚款
     */
    private Double penalty;

    /**
     * 是否有罚款
     */
    private Integer hasPenalty;

    /**
     * 未通过原因
     */
    private String reason;

}