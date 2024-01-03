package com.work.employee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.model.domain.entity.Dept;
import com.work.employee.service.DeptService;
import com.work.employee.mapper.DeptMapper;
import org.springframework.stereotype.Service;

/**
* @author HWQ
* @description 针对表【dept(部门表)】的数据库操作Service实现
* @createDate 2024-01-01 14:35:19
*/
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept>
    implements DeptService{

}




