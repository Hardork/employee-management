package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 器材借用表
 * @TableName equipment_borrow_record
 */
@TableName(value ="equipment_borrow_record")
@Data
public class EquipmentBorrowRecord implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long borrowRecordID;

    /**
     * 借用表id
     */
    private Long borrowID;

    /**
     * 归还人员id
     */
    private String borrowPersonnelNo;

    /**
     * 借出器材数量
     */
    private Integer outNum;

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