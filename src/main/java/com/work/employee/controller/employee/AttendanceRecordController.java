package com.work.employee.controller.employee;

import com.work.employee.common.BaseResponse;
import com.work.employee.common.ResultUtils;
import com.work.employee.model.domain.request.employee.AttendanceRecordListRequest;
import com.work.employee.model.domain.vo.employee.AttendanceRecordListVO;
import com.work.employee.service.AttendanceRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author:HWQ
 * @DateTime:2024/1/1 15:06
 * @Description:
 **/
@RestController
@RequestMapping("/attendanceRecord")
public class AttendanceRecordController {
    @Resource
    private AttendanceRecordService attendanceRecordService;


    @PostMapping("/list")
    public BaseResponse<AttendanceRecordListVO> getAttendanceRecordList(@RequestBody AttendanceRecordListRequest attendanceRecordListRequest){
        AttendanceRecordListVO attendanceRecordListVO = attendanceRecordService.getAttendanceRecordList(attendanceRecordListRequest);
        return ResultUtils.success(attendanceRecordListVO);
    }
}
