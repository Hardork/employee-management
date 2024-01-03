package com.work.employee.service.impl;
import java.util.ArrayList;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.common.ErrorCode;
import com.work.employee.exception.BusinessException;
import com.work.employee.model.domain.entity.DeptEmpDetail;
import com.work.employee.model.domain.entity.WorkTicket;
import com.work.employee.model.domain.request.employee.WorkTicketListRequest;
import com.work.employee.model.domain.vo.employee.*;
import com.work.employee.service.WorkTicketService;
import com.work.employee.mapper.WorkTicketMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author HWQ
* @description 针对表【work_ticket(工单表)】的数据库操作Service实现
* @createDate 2024-01-01 14:35:56
*/
@Service
public class WorkTicketServiceImpl extends ServiceImpl<WorkTicketMapper, WorkTicket>
    implements WorkTicketService{
    @Resource
    private WorkTicketMapper workTicketMapper;

    @Override
    public WorkTicketListVO getWorkTicketList(WorkTicketListRequest workTicketListRequest) {
        if(workTicketListRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //拿出请求对象中的值进行校验
        long pageNo = workTicketListRequest.getPageNo();
        long pageSize = workTicketListRequest.getPageSize();
        String deptNo = workTicketListRequest.getDeptNo();
        Date selectMonth = workTicketListRequest.getSelectMonth();
        String employeeNo = workTicketListRequest.getEmployeeNo();
        Long no = null;
        if (!StringUtils.isEmpty(deptNo)) {
            no = Long.parseLong(deptNo);
        }

        Long employeeID = null;
        if (!StringUtils.isEmpty(employeeNo)) {
            employeeID = Long.parseLong(employeeNo);
        }

        //去数据库获取分页数据
        //缓存中没数据，从数据库中获取，并写入到缓存中
        List<WorkTicketVO> workTicketByDept = workTicketMapper.getWorkTicketByDept(selectMonth, no, employeeID);
        List<WorkTicketVO> res = new ArrayList<>();
        long start = (pageNo - 1) * pageSize;
        long end = start + pageSize;
        for (long i = start; i < end && i < workTicketByDept.size(); i++) {
            res.add(workTicketByDept.get(Integer.parseInt(i + "")));
        }

        //去数据库获取分页数据
        //缓存中没数据，从数据库中获取，并写入到缓存中
        //返回安全的用户数据
        WorkTicketListVO workTicketListVO = new WorkTicketListVO();
        workTicketListVO.setPageNo(pageNo);
        workTicketListVO.setPageSize(pageSize);
        workTicketListVO.setTotalCount(workTicketByDept.size());
        workTicketListVO.setTotalPage(workTicketByDept.size() / pageSize + 1);
        workTicketListVO.setData(res);
        return workTicketListVO;
    }
}




