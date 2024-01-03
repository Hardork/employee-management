package com.work.employee.service;

import com.work.employee.model.domain.entity.WorkTicket;
import com.baomidou.mybatisplus.extension.service.IService;
import com.work.employee.model.domain.request.employee.WorkTicketListRequest;
import com.work.employee.model.domain.vo.employee.WorkTicketListVO;

/**
* @author HWQ
* @description 针对表【work_ticket(工单表)】的数据库操作Service
* @createDate 2024-01-01 14:35:56
*/
public interface WorkTicketService extends IService<WorkTicket> {

    WorkTicketListVO getWorkTicketList(WorkTicketListRequest workTicketListRequest);
}
