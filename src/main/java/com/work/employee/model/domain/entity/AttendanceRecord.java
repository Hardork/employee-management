package com.work.employee.model.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 考勤记录表
 * @TableName attendance_record
 */
@TableName(value ="attendance_record")
@Data
public class AttendanceRecord implements Serializable {
    /**
     * 
     */
    @TableId(value = "arID", type = IdType.AUTO)
    private Long arID;

    /**
     * 出勤日期
     */
    @TableField(value = "attendanceDate")
    private Date attendanceDate;

    /**
     * 退卡时间
     */
    @TableField(value = "returnTime")
    private Date returnTime;

    /**
     * 打卡时间
     */
    @TableField(value = "clockTime")
    private Date clockTime;

    /**
     * 员工id
     */
    @TableField(value = "employeeID")
    private Long employeeID;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}