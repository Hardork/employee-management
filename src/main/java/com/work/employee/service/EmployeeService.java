package com.work.employee.service;

import com.work.employee.model.domain.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.work.employee.model.domain.request.employee.EmployeeListRequest;
import com.work.employee.model.domain.request.equipment.EquipmentListRequest;
import com.work.employee.model.domain.vo.employee.EmployeeListVO;
import com.work.employee.model.domain.vo.equipment.EquipmentListVO;

/**
* @author HWQ
* @description 针对表【employee(员工表)】的数据库操作Service
* @createDate 2024-01-01 14:35:08
*/
public interface EmployeeService extends IService<Employee> {
}
