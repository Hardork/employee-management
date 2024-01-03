package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 器材借用表
 * @TableName equipment_borrow
 */
@TableName(value ="equipment_borrow")
@Data
public class EquipmentBorrow implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long borrowID;

    /**
     * 借用器材编号
     */
    private String equipmentNo;

    /**
     * 借用人员学号或教工号
     */
    private String borrowPersonnelNo;

    /**
     * 正在借用数量
     */
    private Integer leftNum;


    /**
     * 状态 0未批 1通过 2未通过 3借用中 4确认
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

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 0正常 1删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}