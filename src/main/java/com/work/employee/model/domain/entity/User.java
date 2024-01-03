package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账号 是学号或职工号
     */
    private String userAccount;

    /**
     * 用户名 默认是教师学生的名字
     */
    private String username;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 状态 0正常 1封号
     */
    private Integer userStatus;

    /**
     * 用户角色 0普通用户 1管理员
     */
    private Integer userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 0正常 1删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}