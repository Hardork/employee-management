package com.work.employee.model.domain.request.equipment_borrow;

import lombok.Data;

/**
 * @Author:HCJ
 * @DateTime:2023/9/12
 * @Description:
 **/
@Data
public class AddEquipmentBorrowRequest {
    /**
     * 借用器材编号
     */
    private String equipmentNo;

    /**
     * 剩余未归还器材数量
     */
    private Integer leftNum;
}
