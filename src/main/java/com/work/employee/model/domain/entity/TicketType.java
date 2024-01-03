package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 工单种类表
 * @TableName ticket_type
 */
@TableName(value ="ticket_type")
@Data
public class TicketType implements Serializable {
    /**
     * 
     */
    @TableId(value = "ttID", type = IdType.AUTO)
    private Long ttID;

    /**
     * 工单名称
     */
    @TableField(value = "ttName")
    private String ttName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}