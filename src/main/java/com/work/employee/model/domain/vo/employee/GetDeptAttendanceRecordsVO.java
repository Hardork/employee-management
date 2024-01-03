package com.work.employee.model.domain.vo.employee;

import lombok.Data;

import java.util.Date;

/**
 * @Author:HWQ
 * @DateTime:2024/1/1 17:31
 * @Description:
 **/

/**
 * td.deptID,td.deptName,e.employeeID,e.employeeName,
 *     ar.arID,ar.attendanceDate,ar.returnTime,ar.clockTime,
 *     isLate(clockTime) clockStatus, isEarlyDeparture(returnTime) returnStatus
 */

@Data
public class GetDeptAttendanceRecordsVO {
    private Long deptID;
    private String deptName;
    private Long employeeID;
    private String employeeName;
    private Date attendanceDate;
    private Date returnTime;
    private Date clockTime;
    private Boolean clockStatus;
    private Boolean returnStatus;
}
