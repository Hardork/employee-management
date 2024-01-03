package com.work.employee.model.domain.request.equipment_borrow;

import com.work.employee.model.domain.vo.equipment_borrow.EquipmentBorrowVo;
import lombok.Data;

import java.util.List;

/**
 * @Author:HWQ
 * @DateTime:2023/9/14 1:04
 * @Description:
 **/
@Data
public class GetAllListVO {
    private long pageNo;
    private long pageSize;
    private long totalCount;
    private long totalPage;
    private List<EquipmentBorrowVo> data;
}
