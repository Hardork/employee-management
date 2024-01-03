package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 职位表
 * @TableName position
 */
@TableName(value ="position")
@Data
public class Position implements Serializable {
    /**
     * 
     */
    @TableId(value = "positionID", type = IdType.AUTO)
    private Long positionID;

    /**
     * 职位名称
     */
    @TableField(value = "positionName")
    private String positionName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}