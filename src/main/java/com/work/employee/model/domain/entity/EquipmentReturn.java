package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 器材归还申请表
 * @TableName equipment_return
 */
@TableName(value ="equipment_return")
@Data
public class EquipmentReturn implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long returnID;

    /**
     * 借用表id
     */
    private Long borrowID;

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

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除 0-未删除 1-删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}