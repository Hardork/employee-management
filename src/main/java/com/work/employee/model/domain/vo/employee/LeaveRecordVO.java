package com.work.employee.model.domain.vo.employee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author:HWQ
 * @DateTime:2024/1/1 19:30
 * @Description:
 **/
@Data
public class LeaveRecordVO {
    private Long leaveID;

    private Long employeeID;

    private Integer leaveType;

    private String leaveReason;

    private Date startTime;

    private Date endTime;

    private Integer status;
}
