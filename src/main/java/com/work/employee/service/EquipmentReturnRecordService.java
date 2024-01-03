package com.work.employee.service;

import com.work.employee.model.domain.entity.EquipmentReturnRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.request.equipment_return_record.AddEquipmentReturnRecordRequest;

/**
* @author HWQ
* @description 针对表【equipment_return_record(器材归还表记录表)】的数据库操作Service
* @createDate 2023-09-12 20:36:57
*/
public interface EquipmentReturnRecordService extends IService<EquipmentReturnRecord> {

    /**
     * 器材归还审批
     * @param addEquipmentReturnRecordRequest
     * @param loginUser
     * @return
     */
    Boolean addEquipmentReturnRecord(AddEquipmentReturnRecordRequest addEquipmentReturnRecordRequest, User loginUser);

}
