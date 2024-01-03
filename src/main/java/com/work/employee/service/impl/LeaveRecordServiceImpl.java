package com.work.employee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.model.domain.entity.LeaveRecord;
import com.work.employee.service.LeaveRecordService;
import com.work.employee.mapper.LeaveRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author HWQ
* @description 针对表【leave_record(请假记录表)】的数据库操作Service实现
* @createDate 2024-01-01 14:35:45
*/
@Service
public class LeaveRecordServiceImpl extends ServiceImpl<LeaveRecordMapper, LeaveRecord>
    implements LeaveRecordService{

}




