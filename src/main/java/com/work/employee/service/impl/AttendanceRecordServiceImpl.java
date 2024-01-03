package com.work.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.common.ErrorCode;
import com.work.employee.exception.BusinessException;
import com.work.employee.model.domain.entity.AttendanceRecord;
import com.work.employee.model.domain.entity.DeptEmpDetail;
import com.work.employee.model.domain.request.employee.AttendanceRecordListRequest;
import com.work.employee.model.domain.vo.employee.AttendanceRecordListVO;
import com.work.employee.model.domain.vo.employee.EmployeeListVO;
import com.work.employee.model.domain.vo.employee.GetDeptAttendanceRecordsVO;
import com.work.employee.service.AttendanceRecordService;
import com.work.employee.mapper.AttendanceRecordMapper;
import org.apache.commons.collections4.Get;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author HWQ
* @description 针对表【attendance_record(考勤记录表)】的数据库操作Service实现
* @createDate 2024-01-01 14:35:30
*/
@Service
public class AttendanceRecordServiceImpl extends ServiceImpl<AttendanceRecordMapper, AttendanceRecord>
    implements AttendanceRecordService{

    @Resource
    private AttendanceRecordMapper attendanceRecordMapper;

    @Override
    public AttendanceRecordListVO getAttendanceRecordList(AttendanceRecordListRequest attendanceRecordListRequest) {
        if(attendanceRecordListRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //拿出请求对象中的值进行校验
        long pageNo = attendanceRecordListRequest.getPageNo();
        long pageSize = attendanceRecordListRequest.getPageSize();
        String deptNo = attendanceRecordListRequest.getDeptNo();
        String employeeNo = attendanceRecordListRequest.getEmployeeNo();
        Long no = null;
        if (!StringUtils.isEmpty(deptNo)) {
            no = Long.parseLong(deptNo);
        }

        Date selectMonth = attendanceRecordListRequest.getSelectMonth();

        Long employeeID = null;
        if (!StringUtils.isEmpty(employeeNo)) {
            employeeID = Long.parseLong(employeeNo);
        }
        List<GetDeptAttendanceRecordsVO> deptAttendanceRecords = attendanceRecordMapper.getDeptAttendanceRecords(selectMonth, no, employeeID);


        List<GetDeptAttendanceRecordsVO> res = new ArrayList<>();
        long start = (pageNo - 1) * pageSize;
        long end = start + pageSize;
        for (long i = start; i < end && i < deptAttendanceRecords.size(); i++) {
            res.add(deptAttendanceRecords.get(Integer.parseInt(i + "")));
        }

        //去数据库获取分页数据
        //缓存中没数据，从数据库中获取，并写入到缓存中
        //返回安全的用户数据
        AttendanceRecordListVO attendanceRecordListVO = new AttendanceRecordListVO();
        attendanceRecordListVO.setPageNo(pageNo);
        attendanceRecordListVO.setPageSize(pageSize);
        attendanceRecordListVO.setTotalCount(deptAttendanceRecords.size());
        attendanceRecordListVO.setTotalPage(deptAttendanceRecords.size() / pageSize + 1);
        attendanceRecordListVO.setData(res);
        return attendanceRecordListVO;
    }
}




