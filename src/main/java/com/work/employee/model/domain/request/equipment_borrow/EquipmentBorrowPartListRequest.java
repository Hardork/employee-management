package com.work.employee.model.domain.request.equipment_borrow;

import com.work.employee.common.PageRequest;
import lombok.Data;

/**
 * @Author:HCJ
 * @DateTime:2023/9/13
 * @Description:
 **/
@Data
public class EquipmentBorrowPartListRequest extends PageRequest {
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
     * 状态 0未批 1未通过 2借用中 3归还审核 4确认
     */
    private Integer status;
}
