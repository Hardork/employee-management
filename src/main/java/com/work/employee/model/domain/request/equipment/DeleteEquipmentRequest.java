package com.work.employee.model.domain.request.equipment;

import lombok.Data;

/**
 * 器材表
 * @TableName equipment
 */
@Data
public class DeleteEquipmentRequest {

    /**
     * 器材编号
     */
    private Long equipmentID;


}