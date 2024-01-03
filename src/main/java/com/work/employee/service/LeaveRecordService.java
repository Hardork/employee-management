package com.work.employee.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.work.employee.model.domain.entity.LeaveRecord;
import com.work.employee.model.domain.request.employee.LeaveRecordApproveRequest;
import com.work.employee.model.domain.request.employee.LeaveRecordListRequest;
import com.work.employee.model.domain.vo.employee.LeaveRecordListVO;

/**
* @author HWQ
* @description 针对表【leave_record(请假记录表)】的数据库操作Service
* @createDate 2024-01-01 14:35:45
*/
public interface LeaveRecordService extends IService<LeaveRecord> {

    LeaveRecordListVO getLeaveRecordList(LeaveRecordListRequest leaveRecordListRequest);

    Boolean addLeaveRecord(LeaveRecord leaveRecord);

    Boolean approveLeaveRecord(LeaveRecordApproveRequest leaveRecordApproveRequest);
}
