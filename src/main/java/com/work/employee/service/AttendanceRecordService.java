package com.work.employee.service;

import com.work.employee.model.domain.entity.AttendanceRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.work.employee.model.domain.request.employee.AttendanceRecordListRequest;
import com.work.employee.model.domain.vo.employee.AttendanceRecordListVO;

/**
* @author HWQ
* @description 针对表【attendance_record(考勤记录表)】的数据库操作Service
* @createDate 2024-01-01 14:35:30
*/
public interface AttendanceRecordService extends IService<AttendanceRecord> {

    AttendanceRecordListVO getAttendanceRecordList(AttendanceRecordListRequest attendanceRecordListRequest);

}
