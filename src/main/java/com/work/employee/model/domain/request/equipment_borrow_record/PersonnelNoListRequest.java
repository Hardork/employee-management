package com.work.employee.model.domain.request.equipment_borrow_record;

import com.work.employee.common.PageRequest;
import lombok.Data;

/**
 * @Author:HCJ
 * @DateTime:2023/9/13
 * @Description:
 **/
@Data
public class PersonnelNoListRequest extends PageRequest {

    /**
     * 人员编号
     */
    private String personnelNo;
}
