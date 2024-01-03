package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 员工表
 * @TableName employee
 */
@TableName(value ="employee")
@Data
public class Employee implements Serializable {
    /**
     * 
     */
    @TableId(value = "employeeID", type = IdType.AUTO)
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
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 出生年月
     */
    @TableField(value = "birthday")
    private Date birthday;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}