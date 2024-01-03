package com.work.employee.model.domain.request.equipment_return;

import com.work.employee.common.PageRequest;
import lombok.Data;

/**
 * @Author:HWQ
 * @DateTime:2023/9/12 23:19
 * @Description:
 **/
@Data
public class EquipmentReturnPartListRequest extends PageRequest {
    /**
     * 借用器材编号
     */
    private String equipmentNo;

    /**
     * 借用人员学号或教工号
     */
    private String borrowPersonnelNo;

    /**
     * 器材名字
     */
    private String equipmentName;

    /**
     * 状态
     */
    private Integer status;
}
