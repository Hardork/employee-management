package com.work.employee.model.domain.request.equipment_borrow;

import lombok.Data;

@Data
public class UpdateEquipmentBorrowSpecifiedDateRequest {

    /**
     * 订单ID
     */
    private  Integer borrowId;

    /**
     * 延迟借用天数
     */
    private Integer lateDay;
}
