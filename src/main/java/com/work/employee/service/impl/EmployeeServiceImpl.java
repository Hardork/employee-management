package com.work.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.common.ErrorCode;
import com.work.employee.exception.BusinessException;
import com.work.employee.model.domain.entity.Employee;
import com.work.employee.model.domain.entity.Equipment;
import com.work.employee.model.domain.request.employee.EmployeeListRequest;
import com.work.employee.model.domain.request.equipment.EquipmentListRequest;
import com.work.employee.model.domain.vo.employee.EmployeeListVO;
import com.work.employee.model.domain.vo.equipment.EquipmentListVO;
import com.work.employee.service.EmployeeService;
import com.work.employee.mapper.EmployeeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author HWQ
* @description 针对表【employee(员工表)】的数据库操作Service实现
* @createDate 2024-01-01 14:35:08
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{


}




