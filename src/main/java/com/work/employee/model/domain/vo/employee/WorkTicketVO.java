package com.work.employee.model.domain.vo.employee;

import lombok.Data;

import java.util.Date;

/**
 * @Author:HWQ
 * @DateTime:2024/1/1 19:30
 * @Description:
 **/
@Data
public class WorkTicketVO {
    private Long deptID;
    private String deptName;
    private Long employeeID;
    private String employeeName;
    private String ttName;
    private Date startDate;
    private Date endDate;
    private Boolean approvalStatus;
}
