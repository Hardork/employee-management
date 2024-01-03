package com.work.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.common.ErrorCode;
import com.work.employee.exception.BusinessException;
import com.work.employee.exception.ThrowUtils;
import com.work.employee.mapper.EquipmentReturnMapper;
import com.work.employee.model.domain.entity.Equipment;
import com.work.employee.model.domain.entity.EquipmentBorrow;
import com.work.employee.model.domain.entity.EquipmentReturn;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.enums.EquipmentBorrowStatusEnum;
import com.work.employee.model.domain.request.equipment_return.AddEquipmentReturnRequest;
import com.work.employee.model.domain.request.equipment_return.EquipmentReturnPartListRequest;
import com.work.employee.model.domain.vo.equipment_return.EquipmentReturnVo;
import com.work.employee.service.EquipmentBorrowService;
import com.work.employee.service.EquipmentReturnService;
import com.work.employee.service.EquipmentService;
import com.work.employee.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
* @author 洪
* @description 针对表【equipment_return】的数据库操作Service实现
* @createDate 2023-09-12 14:14:11
*/
@Service
public class EquipmentReturnServiceImpl extends ServiceImpl<EquipmentReturnMapper, EquipmentReturn>
    implements EquipmentReturnService {
    @Resource
    private EquipmentReturnMapper equipmentReturnMapper;
    @Resource
    private EquipmentBorrowService equipmentBorrowService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private UserService userService;

    @Override
    public Boolean addEquipmentReturn(AddEquipmentReturnRequest addEquipmentReturnRequest, User loginUser) {
         Long borrowID = addEquipmentReturnRequest.getBorrowID();
         Integer returnNum = addEquipmentReturnRequest.getReturnNum();
         // 校验参数
        if (borrowID == null || returnNum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        // 获取借用订单信息
        EquipmentBorrow borrowInfo = equipmentBorrowService.getById(borrowID);
        if (borrowInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "借用信息不存在");
        }
        // 校验归还数量是否合法
        Integer leftNum = borrowInfo.getLeftNum();// 待归还数量
        if (returnNum < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "部分归还数量异常");
        }
        if (returnNum > leftNum) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "待归还数量超过归还数量");
        } else if(returnNum.equals(leftNum)){
            borrowInfo.setStatus(EquipmentBorrowStatusEnum.CONFIRM.getValue());
        }
        borrowInfo.setLeftNum(leftNum-returnNum);
        // 判断归还订单人员是不是创建订单人员
        if (!borrowInfo.getBorrowPersonnelNo().equals(loginUser.getUserAccount())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "归还人员错误");
        }
        // 校验通过创建归还审核表
        EquipmentReturn equipmentReturn = new EquipmentReturn();
        equipmentReturn.setBorrowID(borrowID);
        equipmentReturn.setBorrowPersonnelNo(loginUser.getUserAccount());
        equipmentReturn.setReturnNum(returnNum);
        equipmentReturn.setStatus(0);
        // 判断是不是有罚款
        Date specifiedDate = borrowInfo.getSpecifiedDate();
        // 如果归还日期超过限定时间
        Date now = new Date();
        if (now.after(specifiedDate)) {
            // 获取对应的器材信息
            QueryWrapper<Equipment> equipmentReturnQueryWrapper = new QueryWrapper<>();
            equipmentReturnQueryWrapper.eq("equipmentNo", borrowInfo.getEquipmentNo());
            Equipment equipmentInfo = equipmentService.getOne(equipmentReturnQueryWrapper);
            // 计算罚款 罚款 = 原始价格 × (逾期天数/365)
            Double equipmentPrice = equipmentInfo.getEquipmentPrice();
            double delayPrice = ((double)getDaysBetween(now, specifiedDate) / 365d);
            double penalty = equipmentPrice * delayPrice;
            String  str = String.format("%.2f",penalty);
            double realPenalty = Double.parseDouble(str);
            equipmentReturn.setPenalty(realPenalty);
        }
        equipmentBorrowService.updateById(borrowInfo);
        boolean save = this.save(equipmentReturn);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建订单失败");
        }
        return true;
    }

    @Override
    public Page<EquipmentReturnVo> equipmentReturnPartList(
            EquipmentReturnPartListRequest equipmentReturnPartListRequest) {
        long current = equipmentReturnPartListRequest.getCurrent();
        long size = equipmentReturnPartListRequest.getPageSize();
        Integer status = equipmentReturnPartListRequest.getStatus();
        ThrowUtils.throwIf(status == null,ErrorCode.PARAMS_ERROR);
        List<EquipmentReturnVo> equipmentReturnVos = equipmentReturnMapper.
                equipmentReturnPartList(equipmentReturnPartListRequest, (current - 1) * size, size);
        Page<EquipmentReturnVo> pageList = new Page<>(current, size);
        pageList.setRecords(equipmentReturnVos);
        return pageList;
    }

    @Override
    public Page<EquipmentReturnVo> getPartOrAllList(EquipmentReturnPartListRequest equipmentReturnPartListRequest, HttpServletRequest request) {

        ThrowUtils.throwIf(equipmentReturnPartListRequest==null,ErrorCode.PARAMS_ERROR);
        Integer status = equipmentReturnPartListRequest.getStatus();
        User loginUser = userService.getLoginUser(request);
        if(loginUser==null){
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        String borrowPersonnelNo = loginUser.getUserAccount();
        long current = equipmentReturnPartListRequest.getCurrent();
        long pageSize = equipmentReturnPartListRequest.getPageSize();
        equipmentReturnPartListRequest.setBorrowPersonnelNo(borrowPersonnelNo);
        List<EquipmentReturnVo> partOrAllList = equipmentReturnMapper.getPartOrAllList(equipmentReturnPartListRequest,
                (current - 1) * pageSize, pageSize);
        Page<EquipmentReturnVo> page = new Page<>(current, pageSize);
        page.setRecords(partOrAllList);
        return page;
    }

    /**
     * 计算两个Date天数差
     * @param cur
     * @param before
     * @return
     */
    public long getDaysBetween(Date before, Date cur) {
        // 将Date对象转换为LocalDate对象
        LocalDate localDate1 = cur.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = before.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long between = ChronoUnit.DAYS.between(localDate1, localDate2);
        // 计算两个LocalDate对象之间的天数差
        return between;
    }
//    public Boolean equipmentLoss(EquipmentLossRequest equipmentLossRequest,User loginUser){
//        Long borrowID =equipmentLossRequest.getBorrowID();
//        Integer returnNum = equipmentLossRequest.getReturnNum();
//        Integer lossNum = equipmentLossRequest.getLossNum();
//        // 校验参数
//        if (borrowID == null || returnNum == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
//        }
//        // 获取借用订单信息
//        EquipmentBorrow borrowInfo = equipmentBorrowService.getById(borrowID);
//        if (borrowInfo == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "借用信息不存在");
//        }
//        // 校验归还数量是否合法
//        Integer leftNum = borrowInfo.getLeftNum();// 待归还数量
//        if (returnNum > leftNum) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "待归还数量超过归还数量");
//        } else if(returnNum.equals(leftNum)){
//            borrowInfo.setStatus(EquipmentBorrowStatusEnum.APPROVED.getValue());
//        }
//        borrowInfo.setLeftNum(leftNum-returnNum);
//        // 校验通过创建归还审核表
//        EquipmentReturn equipmentReturn = new EquipmentReturn();
//        equipmentReturn.setBorrowID(borrowID);
//        equipmentReturn.setBorrowPersonnelNo(loginUser.getUserAccount());
//        equipmentReturn.setReturnNum(returnNum);
//        equipmentReturn.setStatus(0);
//        //如果有丢失
//        if(lossNum>0){
//           //获取对应的器材信息
//            QueryWrapper<Equipment> equipmentQueryWrapper = new QueryWrapper<>();
//            equipmentQueryWrapper.eq("equipmentNo", borrowInfo.getEquipmentNo());
//            //获取第一条符合条件的信息
//            Equipment equipmentInfo = equipmentService.getOne(equipmentQueryWrapper);
//            // 计算罚款 罚款 = 原始价格
//            Double equipmentPrice = equipmentInfo.getEquipmentPrice();
//            double penalty = equipmentPrice;
//            equipmentReturn.setPenalty(penalty);
//            equipmentBorrowService.updateById(borrowInfo);
//            boolean save = this.save(equipmentReturn);
//            if (!save) {
//                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建订单失败");
//            }
//        }
//        return true;
//    }
}




