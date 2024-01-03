package com.work.employee.model.domain.vo.employee;

import com.work.employee.model.domain.entity.WorkTicket;
import lombok.Data;

import java.util.List;

/**
 * @Author:HWQ
 * @DateTime:2023/9/13 13:07
 * @Description:
 **/
@Data
public class WorkTicketListVO {
    private long pageNo;
    private long pageSize;
    private long totalCount;
    private long totalPage;
    private List<WorkTicketVO> data;
}