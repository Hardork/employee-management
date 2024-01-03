package com.work.employee.model.domain.request.employee;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:HWQ
 * @DateTime:2023/9/13 13:04
 * @Description:
 **/
@Data
public class LeaveRecordListRequest implements Serializable {

    private long pageNo;

    private long pageSize;

    /**
     * 部门编号
     */
    private String deptNo;

    /**
     * 员工编号
     */
    private String employeeNo;

    /**
     * 根据月份查询
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date selectMonth;
}
