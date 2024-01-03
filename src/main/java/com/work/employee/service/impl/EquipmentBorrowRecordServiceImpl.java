package com.work.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.common.ErrorCode;
import com.work.employee.exception.BusinessException;
import com.work.employee.exception.ThrowUtils;
import com.work.employee.mapper.EquipmentBorrowRecordMapper;
import com.work.employee.model.domain.entity.EquipmentBorrowRecord;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.enums.DeleteStatusEnum;
import com.work.employee.model.domain.request.equipment_borrow_record.AllListRequest;
import com.work.employee.model.domain.request.equipment_borrow_record.PersonnelNoListRequest;
import com.work.employee.service.EquipmentBorrowRecordService;

import com.work.employee.service.EquipmentService;
import com.work.employee.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* @author 洪
* @description 针对表【equipment_borrow_record(器材借用记录表)】的数据库操作Service实现
* @createDate 2023-09-12 14:14:08
*/
@Service
public class EquipmentBorrowRecordServiceImpl extends ServiceImpl<EquipmentBorrowRecordMapper, EquipmentBorrowRecord>
    implements EquipmentBorrowRecordService {
    @Resource
    private EquipmentBorrowRecordMapper equipmentBorrowRecordMapper;

    @Resource
    private UserService userService;

    @Resource
    private EquipmentService equipmentService;

    @Override
    public boolean addRecord(EquipmentBorrowRecord equipmentBorrowRecord) {
        return save(equipmentBorrowRecord);
    }

    @Override
    public Page<EquipmentBorrowRecord> getMyList(AllListRequest allListRequest, HttpServletRequest request) {
        // 登录校验
        User loginUser = userService.getLoginUser(request);
        String borrowPersonnelNo = loginUser.getUserAccount();
        long size = allListRequest.getPageSize();
        long current = allListRequest.getCurrent();
        if(size<0 || current<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<EquipmentBorrowRecord> qw = new QueryWrapper<>();
        qw.eq("borrowPersonnelNo",borrowPersonnelNo).
                eq("isDelete", DeleteStatusEnum.NOT_DELETE.getValue()).
                orderByDesc("createTime");
        return page(new Page<>(current, size), qw);
    }

    @Override
    public Page<EquipmentBorrowRecord> getListByPersonnelNo(PersonnelNoListRequest personnelNoListRequest, HttpServletRequest request) {

        // 参数校验
        ThrowUtils.throwIf(personnelNoListRequest==null,ErrorCode.PARAMS_ERROR,"参数不能为空");
        String personnelNo = personnelNoListRequest.getPersonnelNo();
        long current = personnelNoListRequest.getCurrent();
        long pageSize = personnelNoListRequest.getPageSize();
        ThrowUtils.throwIf(StringUtils.isBlank(personnelNo),ErrorCode.PARAMS_ERROR,"参数错误");
        QueryWrapper<EquipmentBorrowRecord> qw = new QueryWrapper<>();
        qw.eq("borrowPersonnelNo",personnelNo).
                eq("isDelete", DeleteStatusEnum.NOT_DELETE.getValue()).
                orderByDesc("createTime");
        return page(new Page<>(current,pageSize),qw);
    }

    @Override
    public EquipmentBorrowRecord getDetails(String borrowRecordID, HttpServletRequest request) {
        // 登录校验
        User loginUser = userService.getLoginUser(request);
        // 参数校验
        ThrowUtils.throwIf(StringUtils.isBlank(borrowRecordID),ErrorCode.PARAMS_ERROR,"参数错误");
        QueryWrapper<EquipmentBorrowRecord> qw = new QueryWrapper<>();
        qw.eq("borrowRecordID",borrowRecordID);
        EquipmentBorrowRecord result = getOne(qw);
        ThrowUtils.throwIf(result==null,ErrorCode.SYSTEM_ERROR,"记录不存在");
        return result;
    }

    @Override
    public Page<EquipmentBorrowRecord> getAllList(AllListRequest allListRequest, HttpServletRequest request) {
        long current = allListRequest.getCurrent();
        long pageSize = allListRequest.getPageSize();
        return page(new Page<>(current,pageSize));
    }
}




