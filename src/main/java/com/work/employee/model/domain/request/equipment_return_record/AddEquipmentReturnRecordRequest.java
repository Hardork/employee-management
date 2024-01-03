package com.work.employee.model.domain.request.equipment_return_record;

import lombok.Data;

/**
 * @Author:HWQ
 * @DateTime:2023/9/13 0:14
 * @Description:
 **/
@Data
public class AddEquipmentReturnRecordRequest {
    /**
     * 归还申请表Id
     */
    private Long returnID;

    /**
     * 是否同意
     */
    private Integer status;

    /**
     * 不同意的原因
     */
    private String reason;
}
