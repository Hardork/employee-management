package com.work.employee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.work.employee.annotation.AuthCheck;
import com.work.employee.common.BaseResponse;
import com.work.employee.common.PageResult;
import com.work.employee.common.ResultUtils;
import com.work.employee.constant.UserConstant;
import com.work.employee.model.domain.entity.EquipmentBorrowRecord;
import com.work.employee.model.domain.request.equipment_borrow_record.AllListRequest;
import com.work.employee.model.domain.request.equipment_borrow_record.PersonnelNoListRequest;
import com.work.employee.service.EquipmentBorrowRecordService;
import com.work.employee.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author:HCJ
 * @DateTime:2023/9/12
 * @Description:
 **/
@RestController
@RequestMapping("/equipmentBorrowRecord")
@Slf4j
public class EquipmentBorrowRecordController {

    @Resource
    private EquipmentBorrowRecordService equipmentBorrowRecordService;

    @GetMapping("/getMyList")
    public BaseResponse<PageResult<EquipmentBorrowRecord>> getMyEquipmentBorrowRecordList(AllListRequest allListRequest,HttpServletRequest request){
        Page<EquipmentBorrowRecord> list = equipmentBorrowRecordService.getMyList(allListRequest, request);
        PageResult<EquipmentBorrowRecord> pageResult = PageUtils.getPageResult(list);
        return ResultUtils.success(pageResult);
    }

    @AuthCheck(mustRole = UserConstant.TEACHER)
    @GetMapping("/admin/getListByPersonnelNo")
    public BaseResponse<PageResult<EquipmentBorrowRecord>> getListByPersonnelNo(PersonnelNoListRequest personnelNoListRequest, HttpServletRequest request){
        Page<EquipmentBorrowRecord> list = equipmentBorrowRecordService.getListByPersonnelNo(personnelNoListRequest,request);
        PageResult<EquipmentBorrowRecord> pageResult = PageUtils.getPageResult(list);
        return ResultUtils.success(pageResult);
    }

    @GetMapping("/getDetails")
    public BaseResponse<EquipmentBorrowRecord> getDetails(String borrowRecordID,HttpServletRequest request){
        EquipmentBorrowRecord equipmentBorrowRecord = equipmentBorrowRecordService.getDetails(borrowRecordID,request);
        return ResultUtils.success(equipmentBorrowRecord);
    }

    @AuthCheck(mustRole = UserConstant.TEACHER)
    @GetMapping("/admin/getAllList")
    public BaseResponse<PageResult<EquipmentBorrowRecord>> getAllList(AllListRequest allListRequest, HttpServletRequest request){
        Page<EquipmentBorrowRecord> list = equipmentBorrowRecordService.getAllList(allListRequest,request);
        PageResult<EquipmentBorrowRecord> pageResult = PageUtils.getPageResult(list);
        return ResultUtils.success(pageResult);
    }
}
