package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 工单表
 * @TableName work_ticket
 */
@TableName(value ="work_ticket")
@Data
public class WorkTicket implements Serializable {
    /**
     * 
     */
    @TableId(value = "wtID", type = IdType.AUTO)
    private Long wtID;

    /**
     * 审批状态 0-未批 1-通过 2-未通过
     */
    @TableField(value = "approvalStatus")
    private Integer approvalStatus;

    /**
     * 员工id
     */
    @TableField(value = "employeeID")
    private Long employeeID;

    /**
     * 工单种类id
     */
    @TableField(value = "ttID")
    private Long ttID;

    /**
     * 开始日期
     */
    @TableField(value = "startDate")
    private Date startDate;

    /**
     * 结束日期
     */
    @TableField(value = "endDate")
    private Date endDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}