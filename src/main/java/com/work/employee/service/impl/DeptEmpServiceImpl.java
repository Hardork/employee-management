package com.work.employee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.model.domain.entity.DeptEmp;
import com.work.employee.service.DeptEmpService;
import com.work.employee.mapper.DeptEmpMapper;
import org.springframework.stereotype.Service;

/**
* @author HWQ
* @description 针对表【dept_emp(部门员工表)】的数据库操作Service实现
* @createDate 2024-01-01 14:35:37
*/
@Service
public class DeptEmpServiceImpl extends ServiceImpl<DeptEmpMapper, DeptEmp>
    implements DeptEmpService{

}




