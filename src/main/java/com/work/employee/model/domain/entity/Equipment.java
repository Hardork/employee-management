package com.work.employee.model.domain.entity;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 器材表
 * @TableName equipment
 */
@TableName(value ="equipment")
@Data
public class Equipment implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long equipmentID;

    /**
     * 添加器材人员id
     */
    private String personnelNo;

    /**
     * 器材编号
     */
    private String equipmentNo;

    /**
     * 器材名字
     */
    private String equipmentName;

    /**
     * 器材型号
     */
    private String equipmentModel;

    /**
     * 器材价格
     */
    private Double equipmentPrice;

    /**
     * 器材库存
     */
    private Integer leftNum;

    /**
     * 购买日期
     */
    private Date purchaseDate;

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

    /**
     * 是否可用 0可用 1不可用
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}