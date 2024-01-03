package com.work.employee.model.domain.request.equipment_borrow;

import com.work.employee.common.PageRequest;
import lombok.Data;

/**
 * @Author:HCJ
 * @DateTime:2023/9/13
 * @Description:
 **/
@Data
public class EquipmentBorrowApproveListRequest extends PageRequest {
    private Integer status;
}
