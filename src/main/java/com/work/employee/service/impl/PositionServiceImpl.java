package com.work.employee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.model.domain.entity.Position;
import com.work.employee.service.PositionService;
import com.work.employee.mapper.PositionMapper;
import org.springframework.stereotype.Service;

/**
* @author HWQ
* @description 针对表【position(职位表)】的数据库操作Service实现
* @createDate 2024-01-01 14:35:49
*/
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position>
    implements PositionService{

}




