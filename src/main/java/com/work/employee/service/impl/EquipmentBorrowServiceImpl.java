package com.work.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.common.ErrorCode;
import com.work.employee.exception.BusinessException;
import com.work.employee.exception.ThrowUtils;
import com.work.employee.mapper.EquipmentBorrowMapper;
import com.work.employee.model.domain.entity.Equipment;
import com.work.employee.model.domain.entity.EquipmentBorrow;
import com.work.employee.model.domain.entity.EquipmentBorrowRecord;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.enums.EquipmentBorrowStatusEnum;
import com.work.employee.model.domain.enums.EquipmentStatusEnum;
import com.work.employee.model.domain.request.equipment.DeleteEquipmentRequest;
import com.work.employee.model.domain.request.equipment_borrow.*;
import com.work.employee.model.domain.vo.equipment_borrow.EquipmentBorrowVo;
import com.work.employee.service.EquipmentBorrowRecordService;
import com.work.employee.service.EquipmentBorrowService;
import com.work.employee.service.EquipmentService;
import com.work.employee.service.UserService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
* @author 洪
* @description 针对表【equipment_borrow(器材借用表)】的数据库操作Service实现
* @createDate 2023-09-12 14:14:05
*/
@Service
public class EquipmentBorrowServiceImpl extends ServiceImpl<EquipmentBorrowMapper, EquipmentBorrow>
    implements EquipmentBorrowService {

    @Resource
    private EquipmentBorrowMapper equipmentBorrowMapper;

    @Resource
    private UserService userService;

    @Resource
    private EquipmentBorrowRecordService equipmentBorrowRecordService;

    @Resource
    private EquipmentService equipmentService;

    @Transactional
    @Override
    public boolean addEquipmentBorrow(AddEquipmentBorrowRequest addEquipmentBorrowRequest,HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        ThrowUtils.throwIf(loginUser==null,ErrorCode.NO_LOGIN);
        // 请求参数校验
        String equipmentNo = addEquipmentBorrowRequest.getEquipmentNo();
        if(StringUtils.isBlank(equipmentNo)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        Integer leftNum = addEquipmentBorrowRequest.getLeftNum();
        if(leftNum == null || leftNum <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        // 检查剩余数量
        Equipment equipmentOne = equipmentService.getEquipmentOne(equipmentNo);
        if(leftNum > equipmentOne.getLeftNum()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"库存数量不足");
        }
        // 检查器材状态
        ThrowUtils.throwIf(EquipmentStatusEnum.getEnumByValue(equipmentOne.getStatus()).equals(EquipmentStatusEnum.NOT_AVAILABLE), ErrorCode.PARAMS_ERROR, "器材不可用");
        // 修改剩余数量
        equipmentOne.setLeftNum(equipmentOne.getLeftNum() - leftNum);
        equipmentService.updateById(equipmentOne);
        // 添加借用表
        EquipmentBorrow equipmentBorrow = new EquipmentBorrow();
        equipmentBorrow.setEquipmentNo(equipmentNo);
        equipmentBorrow.setBorrowPersonnelNo(loginUser.getUserAccount());
        equipmentBorrow.setLeftNum(leftNum);
        equipmentBorrow.setStatus(EquipmentBorrowStatusEnum.UNAPPROVED.getValue());
        save(equipmentBorrow);
        return false;
    }

    @Transactional
    @Override
    public boolean approveEquipmentBorrow(ApproveEquipmentBorrowRequest approveEquipmentBorrowRequest,HttpServletRequest request) {
        // 登录校验
        User loginUser = userService.getLoginUser(request);
        // 请求参数校验
        Long borrowID = approveEquipmentBorrowRequest.getBorrowID();
        if(borrowID==null || borrowID<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        Integer borrowStatus = approveEquipmentBorrowRequest.getBorrowStatus();
        if(!(EquipmentBorrowStatusEnum.BORROW.getValue().equals(borrowStatus) ||
                EquipmentBorrowStatusEnum.FAILED.getValue().equals(borrowStatus))){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }

        UpdateWrapper<EquipmentBorrow> uw = new UpdateWrapper<>();
        // update set xxx = x from table where ?=?
        uw.eq("borrowID",borrowID).set("status",borrowStatus);
        boolean updateResult = update(uw);
        if(BooleanUtils.isFalse(updateResult)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新失败");
        }
        // 查询修改完的借用表
        QueryWrapper<EquipmentBorrow> qw = new QueryWrapper<>();
        qw.eq("borrowID", borrowID);
        EquipmentBorrow equipmentBorrow = getOne(qw);

        String personnelNo = loginUser.getUserAccount();
        String equipmentNo = equipmentBorrow.getEquipmentNo();
        Integer leftNum = equipmentBorrow.getLeftNum();
        // 审批未通过
        if(EquipmentBorrowStatusEnum.FAILED.getValue().equals(borrowStatus)){
            // 将借用的器材归还
            equipmentService.addStock(equipmentNo,leftNum);
            return false;
        }



        // 添加一条借用记录

        EquipmentBorrowRecord equipmentBorrowRecord = equipmentBorrowToEquipmentBorrowRecord(equipmentBorrow, leftNum);
        equipmentBorrowRecord.setPersonnelNo(personnelNo);
        boolean addRecordResult = equipmentBorrowRecordService.addRecord(equipmentBorrowRecord);
        if(BooleanUtils.isFalse(addRecordResult)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"更新失败");
        }
        return true;
    }


    @Override
    public Page<EquipmentBorrowVo> getAllEquipmentBorrowList(EquipmentBorrowListRequest equipmentBorrowListRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        ThrowUtils.throwIf(loginUser==null,ErrorCode.NO_LOGIN);
        String borrowPersonnelNo = loginUser.getUserAccount();
        long current = equipmentBorrowListRequest.getCurrent();
        long pageSize = equipmentBorrowListRequest.getPageSize();
        List<EquipmentBorrowVo> equipmentList = equipmentBorrowMapper.getEquipmentList(borrowPersonnelNo,(current - 1) * pageSize, pageSize);
        Page<EquipmentBorrowVo> pageList = new Page<>(current, pageSize);
        pageList.setRecords(equipmentList);
        return pageList;
    }

    @Override
    public Page<EquipmentBorrowVo> getBorrowPartList(EquipmentBorrowPartListRequest equipmentBorrowPartListRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(equipmentBorrowPartListRequest==null,ErrorCode.PARAMS_ERROR);
        Integer status = equipmentBorrowPartListRequest.getStatus();
        ThrowUtils.throwIf(status == null,ErrorCode.PARAMS_ERROR);
        long current = equipmentBorrowPartListRequest.getCurrent();
        long pageSize = equipmentBorrowPartListRequest.getPageSize();
        List<EquipmentBorrowVo> equipmentPartList = equipmentBorrowMapper.getEquipmentPartList(equipmentBorrowPartListRequest
                , (current - 1) * pageSize, pageSize);
        Page<EquipmentBorrowVo> pageList = new Page<>(current,pageSize);
        pageList.setRecords(equipmentPartList);
        return pageList;
    }

    @Override
    public Page<EquipmentBorrowVo> getPartListByLoginUser(EquipmentBorrowPartListRequest equipmentBorrowPartListRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(equipmentBorrowPartListRequest==null,ErrorCode.PARAMS_ERROR);
        Integer status = equipmentBorrowPartListRequest.getStatus();
        ThrowUtils.throwIf(status==null,ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        long current = equipmentBorrowPartListRequest.getCurrent();
        long pageSize = equipmentBorrowPartListRequest.getPageSize();
        List<EquipmentBorrowVo> equipmentPartList = equipmentBorrowMapper.getPartListByLoginUser(
                equipmentBorrowPartListRequest.getStatus(),loginUser.getUserAccount(),
                (current - 1) * pageSize, pageSize);
        Page<EquipmentBorrowVo> pageList = new Page<>();
        pageList.setRecords(equipmentPartList);
        return pageList;

    }

    /**
     * 将借用表实体类的数据导入到借用记录表实体类中
     * @param equipmentBorrow 借用表
     * @param num 器材数量
     * @return 返回一个借用实体类
     */
    private EquipmentBorrowRecord equipmentBorrowToEquipmentBorrowRecord(EquipmentBorrow equipmentBorrow,int num){
        EquipmentBorrowRecord equipmentBorrowRecord = new EquipmentBorrowRecord();
        equipmentBorrowRecord.setBorrowID(equipmentBorrow.getBorrowID());
        equipmentBorrowRecord.setBorrowPersonnelNo(equipmentBorrow.getBorrowPersonnelNo());
        equipmentBorrowRecord.setOutNum(num);
        return equipmentBorrowRecord;
    }

    /*
     @Override
    public boolean reduceStock(String equipmentNo, Integer leftNum) {
        //参数校验
        if(StringUtils.isBlank(equipmentNo)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
        if(leftNum==null || leftNum<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
        // 查询库存
        QueryWrapper<Equipment> qw = new QueryWrapper<>();
        qw.eq("equipmentNo",equipmentNo);
        Equipment equipment = getOne(qw);
        if(equipment ==null || equipment.getLeftNum() < leftNum){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
        //修改库存
        UpdateWrapper<Equipment> uw = new UpdateWrapper<>();
        uw.set("leftNum",equipment.getLeftNum()-leftNum).eq("equipmentNo",equipmentNo);
        return update(uw);
    }
*/

    @Override
    public boolean updateEquipmentBorrowSpecifiedDate(UpdateEquipmentBorrowSpecifiedDateRequest updateEquipmentBorrowSpecifiedDateRequest,HttpServletRequest request){
        //参数校验
        if(updateEquipmentBorrowSpecifiedDateRequest.getLateDay()==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
        if(updateEquipmentBorrowSpecifiedDateRequest.getBorrowId()==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
        // 查询时间
        QueryWrapper<EquipmentBorrow> qw = new QueryWrapper<>();
        qw.eq("borrowId",updateEquipmentBorrowSpecifiedDateRequest.getBorrowId());
        EquipmentBorrow equipmentBorrow = getOne(qw);
        if(updateEquipmentBorrowSpecifiedDateRequest.getLateDay()<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
        //修改库存
        UpdateWrapper<EquipmentBorrow> uw = new UpdateWrapper<>();
        uw.set("specifiedDate",new Date(equipmentBorrow.getSpecifiedDate().getTime()+updateEquipmentBorrowSpecifiedDateRequest.getLateDay() * 86400000)).eq("borrowId",updateEquipmentBorrowSpecifiedDateRequest.getBorrowId());
        return update(uw);
    }

    @Override
    public Boolean deleteEquipment(DeleteEquipmentRequest deleteEquipmentRequest) {
        ThrowUtils.throwIf(deleteEquipmentRequest==null,ErrorCode.PARAMS_ERROR,"参数不能为空");
        Long equipmentID = deleteEquipmentRequest.getEquipmentID();
        ThrowUtils.throwIf( equipmentID == null,
                ErrorCode.PARAMS_ERROR,"参数不能为空");
        List<EquipmentBorrow> list = list();
        for (EquipmentBorrow equipmentBorrow : list) {
            if(equipmentBorrow.getStatus().equals(EquipmentBorrowStatusEnum.BORROW.getValue())){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
        QueryWrapper<Equipment> qw = new QueryWrapper<>();
        qw.eq("equipmentID", equipmentID);
        boolean result = equipmentService.remove(qw);
        if(!result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除器材失败");
        }
        return true;
    }
}




