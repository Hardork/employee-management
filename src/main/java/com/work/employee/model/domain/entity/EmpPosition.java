package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 员工职位表
 * @TableName emp_position
 */
@TableName(value ="emp_position")
@Data
public class EmpPosition implements Serializable {
    /**
     * 
     */
    @TableId(value = "epID", type = IdType.AUTO)
    private Long epID;

    /**
     * 用户id
     */
    @TableField(value = "employeeID")
    private Long employeeID;

    /**
     * 职位id
     */
    @TableField(value = "positionID")
    private Long positionID;

    /**
     * 任职时间
     */
    @TableField(value = "fromDate")
    private Date fromDate;

    /**
     * 离任时间
     */
    @TableField(value = "toDate")
    private Date toDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}