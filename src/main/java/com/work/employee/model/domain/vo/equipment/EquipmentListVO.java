package com.work.employee.model.domain.vo.equipment;

import com.work.employee.model.domain.entity.Equipment;
import lombok.Data;

import java.util.List;

/**
 * @Author:HWQ
 * @DateTime:2023/9/13 13:07
 * @Description:
 **/
@Data
public class EquipmentListVO {
    private long pageNo;
    private long pageSize;
    private long totalCount;
    private long totalPage;
    private List<Equipment> data;
}