package com.work.employee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.mapper.PersonnelMapper;
import com.work.employee.model.domain.entity.Personnel;
import com.work.employee.service.PersonnelService;
import org.springframework.stereotype.Service;

/**
 * @author 洪
 * @description 针对表【personnel(教师学生信息表)】的数据库操作Service实现
 * @createDate 2023-09-12 14:14:14
 */
@Service
public class PersonnelServiceImpl extends ServiceImpl<PersonnelMapper, Personnel>
        implements PersonnelService {

}




