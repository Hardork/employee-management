package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 教师学生信息表
 * @TableName personnel
 */
@TableName(value ="personnel")
@Data
public class Personnel implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long personnelID;

    /**
     * 学号或职工号
     */
    private String personnelNo;

    /**
     * 人员名字
     */
    private String personnelName;

    /**
     * 人员角色 0学生 1教师
     */
    private Integer personnelRole;

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