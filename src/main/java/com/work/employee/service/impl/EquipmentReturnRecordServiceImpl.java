package com.work.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.common.ErrorCode;
import com.work.employee.exception.BusinessException;
import com.work.employee.model.domain.entity.*;
import com.work.employee.mapper.EquipmentReturnRecordMapper;
import com.work.employee.model.domain.enums.EquipmentBorrowStatusEnum;
import com.work.employee.model.domain.enums.EquipmentReturnStatusEnum;
import com.work.employee.model.domain.request.equipment_return_record.AddEquipmentReturnRecordRequest;
import com.work.employee.service.EquipmentBorrowService;
import com.work.employee.service.EquipmentReturnRecordService;
import com.work.employee.service.EquipmentReturnService;
import com.work.employee.service.EquipmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author HWQ
* @description 针对表【equipment_return_record(器材归还表记录表)】的数据库操作Service实现
* @createDate 2023-09-12 20:36:57
*/
@Service
public class EquipmentReturnRecordServiceImpl extends ServiceImpl<EquipmentReturnRecordMapper, EquipmentReturnRecord>
    implements EquipmentReturnRecordService {

    @Resource
    private EquipmentReturnService equipmentReturnService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private EquipmentBorrowService equipmentBorrowService;

    @Transactional
    @Override
    public Boolean addEquipmentReturnRecord(AddEquipmentReturnRecordRequest addEquipmentReturnRecordRequest, User loginUser) {
        Long returnID = addEquipmentReturnRecordRequest.getReturnID();
        Integer status = addEquipmentReturnRecordRequest.getStatus();
        String reason = addEquipmentReturnRecordRequest.getReason();
        if (returnID == null || status == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 查询用户归还申请表
        QueryWrapper<EquipmentReturn> equipmentReturnQueryWrapper = new QueryWrapper<>();
        equipmentReturnQueryWrapper.eq("returnID", returnID);
        EquipmentReturn equipmentReturnInfo = equipmentReturnService.getOne(equipmentReturnQueryWrapper);
        Long borrowID = equipmentReturnInfo.getBorrowID();
        String borrowPersonnelNo = equipmentReturnInfo.getBorrowPersonnelNo();
        Integer returnNum = equipmentReturnInfo.getReturnNum();
        QueryWrapper<EquipmentBorrow> equipmentBorrowQueryWrapper = new QueryWrapper<>();
        equipmentBorrowQueryWrapper.eq("borrowID", borrowID);
        EquipmentBorrow equipmentBorrowInfo = equipmentBorrowService.getOne(equipmentBorrowQueryWrapper);
        // 1. 管理员同意
        if (EquipmentReturnStatusEnum.AGREE.equals(EquipmentReturnStatusEnum.getEnumByValue(status))) {
            equipmentReturnInfo.setStatus(EquipmentReturnStatusEnum.AGREE.getValue());
            boolean updateEquipmentReturn = equipmentReturnService.updateById(equipmentReturnInfo);
            if (!updateEquipmentReturn) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            // 1.4修改系统库存
            QueryWrapper<Equipment> equipmentQueryWrapper = new QueryWrapper<>();
            equipmentQueryWrapper.eq("equipmentNo", equipmentBorrowInfo.getEquipmentNo());
            Equipment equipmentInfo = equipmentService.getOne(equipmentQueryWrapper);
            Integer afterNum = equipmentInfo.getLeftNum() + returnNum;
            // 对应器材库存增加用户归还的数量
            equipmentInfo.setLeftNum(afterNum);
            boolean updateEquipmentInfo = equipmentService.updateById(equipmentInfo);
            if (!updateEquipmentInfo) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            // 1.5 生成equipment_return_record表
            EquipmentReturnRecord equipmentReturnRecord = new EquipmentReturnRecord();
            equipmentReturnRecord.setBorrowPersonnelNo(borrowPersonnelNo);
            equipmentReturnRecord.setBorrowID(borrowID);
            equipmentReturnRecord.setReturnNum(returnNum);
            equipmentReturnRecord.setPersonnelNo(loginUser.getUserAccount());
            boolean saveEquipmentReturnRecord = this.save(equipmentReturnRecord);
            if (!saveEquipmentReturnRecord) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }

        //  2.管理员不同意
        if (EquipmentReturnStatusEnum.DISAGREE.equals(EquipmentReturnStatusEnum.getEnumByValue(status))) {
            // 2.1 将equipment_return表的status字段改为未通过,并设置未通过原因
            equipmentReturnInfo.setStatus(EquipmentReturnStatusEnum.DISAGREE.getValue());
            equipmentReturnInfo.setReason(reason);
            boolean updateEquipmentReturnInfo = equipmentReturnService.updateById(equipmentReturnInfo);
            if (!updateEquipmentReturnInfo) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            // 2.2 修改equipment_borrow数量
            int leftNum = equipmentBorrowInfo.getLeftNum() + returnNum;
            equipmentBorrowInfo.setLeftNum(leftNum);
            Integer s = equipmentBorrowInfo.getStatus();
            EquipmentBorrowStatusEnum statusEnum = EquipmentBorrowStatusEnum.getEnumByValue(s);
            if(statusEnum == null){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            if(statusEnum.equals(EquipmentBorrowStatusEnum.APPROVED)){
                equipmentBorrowInfo.setStatus(EquipmentBorrowStatusEnum.BORROW.getValue());
            }
            boolean result = equipmentBorrowService.updateById(equipmentBorrowInfo);
            if(!result){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
        return true;
    }
}




