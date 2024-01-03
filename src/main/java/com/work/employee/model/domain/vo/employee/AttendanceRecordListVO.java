package com.work.employee.model.domain.vo.employee;

import com.work.employee.model.domain.entity.AttendanceRecord;
import com.work.employee.model.domain.entity.Employee;
import lombok.Data;

import java.util.List;

/**
 * @Author:HWQ
 * @DateTime:2023/9/13 13:07
 * @Description:
 **/
@Data
public class AttendanceRecordListVO {
    private long pageNo;
    private long pageSize;
    private long totalCount;
    private long totalPage;
    private List<GetDeptAttendanceRecordsVO> data;
}