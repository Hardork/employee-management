package com.work.employee.service;

import com.work.employee.model.domain.entity.DeptEmpDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.work.employee.model.domain.request.employee.EmployeeListRequest;
import com.work.employee.model.domain.vo.employee.EmployeeListVO;

/**
* @author HWQ
* @description 针对表【dept_emp_detail】的数据库操作Service
* @createDate 2024-01-01 14:57:14
*/
public interface DeptEmpDetailService extends IService<DeptEmpDetail> {

    EmployeeListVO getEmployeeList(EmployeeListRequest employeeListRequest);
}
