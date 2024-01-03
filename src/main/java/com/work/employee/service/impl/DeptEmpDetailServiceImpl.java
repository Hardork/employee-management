package com.work.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.common.ErrorCode;
import com.work.employee.exception.BusinessException;
import com.work.employee.model.domain.entity.DeptEmpDetail;
import com.work.employee.model.domain.entity.Employee;
import com.work.employee.model.domain.request.employee.EmployeeListRequest;
import com.work.employee.model.domain.vo.employee.EmployeeListVO;
import com.work.employee.service.DeptEmpDetailService;
import com.work.employee.mapper.DeptEmpDetailMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author HWQ
* @description 针对表【dept_emp_detail】的数据库操作Service实现
* @createDate 2024-01-01 14:57:14
*/
@Service
public class DeptEmpDetailServiceImpl extends ServiceImpl<DeptEmpDetailMapper, DeptEmpDetail>
    implements DeptEmpDetailService{


    @Override
    public EmployeeListVO getEmployeeList(EmployeeListRequest employeeListRequest) {
        if(employeeListRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //拿出请求对象中的值进行校验
        QueryWrapper<DeptEmpDetail> EmployeeQueryWrapper = new QueryWrapper<>();

        long pageNo = employeeListRequest.getPageNo();
        long pageSize = employeeListRequest.getPageSize();
        String deptName = employeeListRequest.getDeptName();
        String name = employeeListRequest.getName();

        if (!StringUtils.isEmpty(name)) {
            EmployeeQueryWrapper.eq("employeeName", name);
        }

        if (!StringUtils.isEmpty(deptName)) {
            EmployeeQueryWrapper.eq("deptName", deptName);
        }

        //去数据库获取分页数据
        //缓存中没数据，从数据库中获取，并写入到缓存中
        Page<DeptEmpDetail> EmployeePage = this.page(new Page<>(pageNo,pageSize),EmployeeQueryWrapper);
        List<DeptEmpDetail> EmployeeList = EmployeePage.getRecords();
        //返回安全的用户数据
        EmployeeListVO EmployeeListVO = new EmployeeListVO();
        EmployeeListVO.setPageNo(EmployeePage.getCurrent());
        EmployeeListVO.setPageSize(EmployeePage.getSize());
        EmployeeListVO.setTotalCount(EmployeePage.getTotal());
        EmployeeListVO.setTotalPage(EmployeePage.getPages());
        EmployeeListVO.setData(EmployeeList);
        return EmployeeListVO;
    }
}




