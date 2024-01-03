package com.work.employee.model.domain.request.equipment_return;

import lombok.Data;

/**
 * @Author:HWQ
 * @DateTime:2023/9/12 19:41
 * @Description:
 **/
@Data
public class AddEquipmentReturnRequest {
    /**
     * 借用表id
     */
    private Long borrowID;


    /**
     * 归还数量
     */
    private Integer returnNum;
}
