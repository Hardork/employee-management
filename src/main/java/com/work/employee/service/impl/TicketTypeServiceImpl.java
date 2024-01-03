package com.work.employee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.model.domain.entity.TicketType;
import com.work.employee.service.TicketTypeService;
import com.work.employee.mapper.TicketTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author HWQ
* @description 针对表【ticket_type(工单种类表)】的数据库操作Service实现
* @createDate 2024-01-01 14:35:52
*/
@Service
public class TicketTypeServiceImpl extends ServiceImpl<TicketTypeMapper, TicketType>
    implements TicketTypeService{

}




