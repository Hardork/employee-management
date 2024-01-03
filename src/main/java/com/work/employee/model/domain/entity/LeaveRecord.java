package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 请假记录表
 * @TableName leave_record
 */
@TableName(value ="leave_record")
@Data
public class LeaveRecord implements Serializable {
    /**
     * 
     */
    @TableId(value = "leaveID", type = IdType.AUTO)
    private Long leaveID;

    /**
     * 员工ID
     */
    @TableField(value = "employeeID")
    private Long employeeID;

    /**
     * 请假类型 0-事假 1-婚假 2-其他
     */
    @TableField(value = "leaveType")
    private Integer leaveType;

    /**
     * 请假原因
     */
    @TableField(value = "leaveReason")
    private String leaveReason;

    /**
     * 开始时间
     */
    @TableField(value = "startTime")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField(value = "endTime")
    private Date endTime;

    /**
     * 审批状态
     */
    @TableField(value = "status")
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}