package com.work.employee.controller.employee;

import com.work.employee.common.BaseResponse;
import com.work.employee.common.ResultUtils;
import com.work.employee.model.domain.entity.LeaveRecord;
import com.work.employee.model.domain.request.employee.LeaveRecordApproveRequest;
import com.work.employee.model.domain.request.employee.LeaveRecordListRequest;
import com.work.employee.model.domain.vo.employee.LeaveRecordListVO;
import com.work.employee.service.LeaveRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:HCJ
 * @DateTime:2024/1/3
 * @Description:
 **/
@RestController
@RequestMapping("/leaveRecord")
public class LeaveRecordController {

    @Resource
    private LeaveRecordService leaveRecordService;

    @PostMapping("/list")
    public BaseResponse<LeaveRecordListVO> getLeaveRecordList(@RequestBody LeaveRecordListRequest leaveRecordListRequest){
        LeaveRecordListVO leaveRecordListVO = leaveRecordService.getLeaveRecordList(leaveRecordListRequest);
        return ResultUtils.success(leaveRecordListVO);
    }

    @PostMapping("/add")
    public BaseResponse<Boolean> addLeaveRecordList(@RequestBody LeaveRecord leaveRecord){
        Boolean res = leaveRecordService.addLeaveRecord(leaveRecord);
        return ResultUtils.success(res);
    }

    @PostMapping("/approve")
    public BaseResponse<Boolean> approveLeaveRecordList(@RequestBody LeaveRecordApproveRequest leaveRecordApproveRequest){
        Boolean res = leaveRecordService.approveLeaveRecord(leaveRecordApproveRequest);
        return ResultUtils.success(res);
    }
}
