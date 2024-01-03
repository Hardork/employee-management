package com.work.employee.model.domain.request.employee;

import lombok.Data;

/**
 * @Author:HCJ
 * @DateTime:2024/1/3
 * @Description:
 **/
@Data
public class LeaveRecordApproveRequest {
    private Long leaveID;

    /**
     * 审批状态 0-未批 1-通过 2-未通过
     */
    private Integer approvalStatus;
}
