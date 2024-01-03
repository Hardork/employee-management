package com.work.employee.model.domain.request.employee;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author:HCJ
 * @DateTime:2024/1/3
 * @Description:
 **/
@Data
public class WorkTicketApproveRequest {
    private Long wtID;

    /**
     * 审批状态 0-未批 1-通过 2-未通过
     */
    private Integer approvalStatus;
}
