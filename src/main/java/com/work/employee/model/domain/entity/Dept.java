package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 部门表
 * @TableName dept
 */
@TableName(value ="dept")
@Data
public class Dept implements Serializable {
    /**
     * 
     */
    @TableId(value = "deptID", type = IdType.AUTO)
    private Long deptID;

    /**
     * 部门名称
     */
    @TableField(value = "deptName")
    private String deptName;

    /**
     * 部门简介
     */
    @TableField(value = "deptIntroduction")
    private String deptIntroduction;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}