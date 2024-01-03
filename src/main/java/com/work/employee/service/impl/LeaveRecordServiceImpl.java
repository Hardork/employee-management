package com.work.employee.service.impl;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.common.ErrorCode;
import com.work.employee.exception.BusinessException;
import com.work.employee.model.domain.entity.DeptEmpDetail;
import com.work.employee.model.domain.entity.LeaveRecord;
import com.work.employee.model.domain.entity.WorkTicket;
import com.work.employee.model.domain.request.employee.LeaveRecordApproveRequest;
import com.work.employee.model.domain.request.employee.LeaveRecordListRequest;
import com.work.employee.model.domain.vo.employee.EmployeeListVO;
import com.work.employee.model.domain.vo.employee.LeaveRecordListVO;
import com.work.employee.service.LeaveRecordService;
import com.work.employee.mapper.LeaveRecordMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author HWQ
* @description 针对表【leave_record(请假记录表)】的数据库操作Service实现
* @createDate 2024-01-01 14:35:45
*/
@Service
public class LeaveRecordServiceImpl extends ServiceImpl<LeaveRecordMapper, LeaveRecord>
    implements LeaveRecordService{

    @Override
    public LeaveRecordListVO getLeaveRecordList(LeaveRecordListRequest leaveRecordListRequest) {


        if(leaveRecordListRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //拿出请求对象中的值进行校验
        QueryWrapper<LeaveRecord> EmployeeQueryWrapper = new QueryWrapper<>();

        long pageNo = leaveRecordListRequest.getPageNo();
        long pageSize = leaveRecordListRequest.getPageSize();
        String employeeNo = leaveRecordListRequest.getEmployeeNo();
        Integer status = leaveRecordListRequest.getStatus();

        if (!StringUtils.isEmpty(employeeNo)) {
            EmployeeQueryWrapper.eq("employeeID", Long.parseLong(employeeNo));
        }
        if (status != null) {
            EmployeeQueryWrapper.eq("status",leaveRecordListRequest.getStatus());
        }

        //去数据库获取分页数据
        //缓存中没数据，从数据库中获取，并写入到缓存中
        Page<LeaveRecord> EmployeePage = this.page(new Page<>(pageNo,pageSize),EmployeeQueryWrapper);
        List<LeaveRecord> EmployeeList = EmployeePage.getRecords();
        //返回安全的用户数据
        LeaveRecordListVO EmployeeListVO = new LeaveRecordListVO();
        EmployeeListVO.setPageNo(EmployeePage.getCurrent());
        EmployeeListVO.setPageSize(EmployeePage.getSize());
        EmployeeListVO.setTotalCount(EmployeePage.getTotal());
        EmployeeListVO.setTotalPage(EmployeePage.getPages());
        EmployeeListVO.setData(EmployeeList);
        return EmployeeListVO;
    }

    @Override
    public Boolean addLeaveRecord(LeaveRecord leaveRecord) {
        return this.save(leaveRecord);
    }

    @Override
    public Boolean approveLeaveRecord(LeaveRecordApproveRequest leaveRecordApproveRequest) {
        UpdateWrapper<LeaveRecord> uw = new UpdateWrapper<>();
        uw.eq("leaveID",leaveRecordApproveRequest.getLeaveID()).set("status"
                ,leaveRecordApproveRequest.getApprovalStatus());
        return this.update(uw);
    }
}




