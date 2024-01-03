package com.work.employee.model.domain.request.equipment_borrow;

import lombok.Data;

/**
 * @Author:HCJ
 * @DateTime:2023/9/12
 * @Description:
 **/
@Data
public class ApproveEquipmentBorrowRequest {
    /**
     * 借用表id
     */
    private Long borrowID;

    /**
     * 借用表状态  0未批 1通过 2未通过 3借用中 4确认
     */
    private Integer borrowStatus;

    /**
     * 未通过备注
     */
    private String reason;
}
