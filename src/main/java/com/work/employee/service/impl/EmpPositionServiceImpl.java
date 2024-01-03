package com.work.employee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.model.domain.entity.EmpPosition;
import com.work.employee.service.EmpPositionService;
import com.work.employee.mapper.EmpPositionMapper;
import org.springframework.stereotype.Service;

/**
* @author HWQ
* @description 针对表【emp_position(员工职位表)】的数据库操作Service实现
* @createDate 2024-01-01 14:35:41
*/
@Service
public class EmpPositionServiceImpl extends ServiceImpl<EmpPositionMapper, EmpPosition>
    implements EmpPositionService{

}




