package com.work.employee.model.domain.request.equipment;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:HWQ
 * @DateTime:2023/9/13 13:04
 * @Description:
 **/
@Data
public class EquipmentListRequest implements Serializable {
    private long pageNo;
    private long pageSize;
    /**
     * 器材编号
     */
    private String equipmentNo;

    /**
     * 器材名字
     */
    private String equipmentName;

    /**
     * 器材型号
     */
    private String equipmentModel;
}
