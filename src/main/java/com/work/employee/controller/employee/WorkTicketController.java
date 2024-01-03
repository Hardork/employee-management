package com.work.employee.controller.employee;

import com.work.employee.common.BaseResponse;
import com.work.employee.common.ResultUtils;
import com.work.employee.model.domain.entity.WorkTicket;
import com.work.employee.model.domain.request.employee.AttendanceRecordListRequest;
import com.work.employee.model.domain.request.employee.WorkTicketApproveRequest;
import com.work.employee.model.domain.request.employee.WorkTicketListRequest;
import com.work.employee.model.domain.vo.employee.AttendanceRecordListVO;
import com.work.employee.model.domain.vo.employee.WorkTicketListVO;
import com.work.employee.service.WorkTicketService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author:HWQ
 * @DateTime:2024/1/1 19:23
 * @Description:
 **/
@RestController
@RequestMapping("/workTicket")
public class WorkTicketController {

    @Resource
    private WorkTicketService workTicketService;

    @PostMapping("/list")
    public BaseResponse<WorkTicketListVO> getWorkTicketList(@RequestBody WorkTicketListRequest workTicketListRequest){
        WorkTicketListVO workTicketListVO = workTicketService.getWorkTicketList(workTicketListRequest);
        return ResultUtils.success(workTicketListVO);
    }

    @PostMapping("/add")
    public BaseResponse<Boolean> addWorkTicketList(@RequestBody WorkTicket workTicket){
        Boolean res = workTicketService.addWorkTicket(workTicket);
        return ResultUtils.success(res);
    }

    @PostMapping("/approve")
    public BaseResponse<Boolean> approveWorkTicketList(@RequestBody WorkTicketApproveRequest workTicketApproveRequest){
        Boolean res = workTicketService.approveWorkTicket(workTicketApproveRequest);
        return ResultUtils.success(res);
    }
}
