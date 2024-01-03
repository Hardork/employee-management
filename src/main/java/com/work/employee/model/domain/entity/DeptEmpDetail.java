package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName dept_emp_detail
 */
@TableName(value ="dept_emp_detail")
@Data
public class DeptEmpDetail implements Serializable {
    /**
     * 
     */
    @TableField(value = "employeeID")
    private Long employeeID;

    /**
     * 员工名字
     */
    @TableField(value = "employeeName")
    private String employeeName;

    /**
     * 家庭住址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 员工简介
     */
    @TableField(value = "employeeIntroduction")
    private String employeeIntroduction;

    /**
     * 基础工资
     */
    @TableField(value = "baseSalary")
    private Integer baseSalary;

    /**
     * 出生年月
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 
     */
    @TableField(value = "deptID")
    private Long deptID;

    /**
     * 部门名称
     */
    @TableField(value = "deptName")
    private String deptName;

    /**
     * 职位名称
     */
    @TableField(value = "positionName")
    private String positionName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}