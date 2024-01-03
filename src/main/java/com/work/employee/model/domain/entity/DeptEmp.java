package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 部门员工表
 * @TableName dept_emp
 */
@TableName(value ="dept_emp")
@Data
public class DeptEmp implements Serializable {
    /**
     * 
     */
    @TableId(value = "deID", type = IdType.AUTO)
    private Long deID;

    /**
     * 员工id
     */
    @TableField(value = "employeeID")
    private Long employeeID;

    /**
     * 部门id
     */
    @TableField(value = "deptID")
    private Long deptID;

    /**
     * 加入时间
     */
    @TableField(value = "fromDate")
    private Date fromDate;

    /**
     * 离开时间
     */
    @TableField(value = "toDate")
    private Date toDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}