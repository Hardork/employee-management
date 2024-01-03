package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 器材归还表记录表
 * @TableName equipment_return_record
 */
@TableName(value ="equipment_return_record")
@Data
public class EquipmentReturnRecord implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long returnRecordID;

    /**
     * 借用人员学号或教工号
     */
    private String borrowPersonnelNo;

    /**
     * 借用表id
     */
    private Long borrowID;

    /**
     * 归还数量
     */
    private Integer returnNum;

    /**
     * 审核人
     */
    private String personnelNo;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 0正常 1删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}