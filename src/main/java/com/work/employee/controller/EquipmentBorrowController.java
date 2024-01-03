package com.work.employee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.work.employee.annotation.AuthCheck;
import com.work.employee.common.BaseResponse;
import com.work.employee.common.PageResult;
import com.work.employee.common.ResultUtils;
import com.work.employee.constant.UserConstant;
import com.work.employee.model.domain.request.equipment_borrow.*;
import com.work.employee.model.domain.vo.equipment_borrow.EquipmentBorrowVo;
import com.work.employee.service.EquipmentBorrowService;
import com.work.employee.utils.PageUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author:HCJ
 * @DateTime:2023/9/12
 * @Description:
 **/
@RestController
@RequestMapping("/equipmentBorrow")
public class EquipmentBorrowController {

    @Resource
    private EquipmentBorrowService equipmentBorrowService;

    @PostMapping("/add")
    public BaseResponse<Boolean> addEquipmentBorrow(@RequestBody AddEquipmentBorrowRequest addEquipmentBorrowRequest, HttpServletRequest request){
        boolean result = equipmentBorrowService.addEquipmentBorrow(addEquipmentBorrowRequest,request);
        return ResultUtils.success(result);
    }

    @AuthCheck(mustRole = UserConstant.TEACHER)
    @PostMapping("/admin/approve")
    public BaseResponse<Boolean> approveEquipmentBorrow(@RequestBody ApproveEquipmentBorrowRequest approveEquipmentBorrowRequest,HttpServletRequest request){
        boolean result = equipmentBorrowService.approveEquipmentBorrow(approveEquipmentBorrowRequest,request);
        return ResultUtils.success(result);
    }

    /**
     * 用户获取自己所有的借用订单信息
     * @param equipmentBorrowListRequest
     * @param request
     * @return
     */
    @PostMapping("/getMyAllList")
    public BaseResponse<PageResult<EquipmentBorrowVo>> getAllEquipmentBorrowList(@RequestBody EquipmentBorrowListRequest equipmentBorrowListRequest, HttpServletRequest request){
        Page<EquipmentBorrowVo> list = equipmentBorrowService.getAllEquipmentBorrowList(equipmentBorrowListRequest, request);
        PageResult<EquipmentBorrowVo> pageResult = PageUtils.getPageResult(list);
        return ResultUtils.success(pageResult);
    }

    /**
     * 教师获取所有待审批的借用订单
     * @param equipmentBorrowPartListRequest
     * @param request
     * @return
     */
    @AuthCheck(mustRole = UserConstant.TEACHER)
    @PostMapping("/getPartList")
    public BaseResponse<PageResult<EquipmentBorrowVo>> getBorrowPartList(
            @RequestBody EquipmentBorrowPartListRequest equipmentBorrowPartListRequest,
            HttpServletRequest request){
        Page<EquipmentBorrowVo> list = equipmentBorrowService.getBorrowPartList(equipmentBorrowPartListRequest, request);
        PageResult<EquipmentBorrowVo> pageResult = PageUtils.getPageResult(list);
        return ResultUtils.success(pageResult);
    }

    /**
     * 用户获取自己进行中的订单
     * @param equipmentBorrowPartListRequest
     * @param request
     * @return
     */
    @PostMapping("/getPartListByLoginUser")
    public BaseResponse<PageResult<EquipmentBorrowVo>> getPartListByLoginUser(@RequestBody EquipmentBorrowPartListRequest equipmentBorrowPartListRequest, HttpServletRequest request){
        Page<EquipmentBorrowVo> list = equipmentBorrowService.getPartListByLoginUser(equipmentBorrowPartListRequest, request);
        PageResult<EquipmentBorrowVo> pageResult = PageUtils.getPageResult(list);
        return ResultUtils.success(pageResult);
    }

    @PostMapping("/updateEquipmentBorrowSpecifiedDate")
    public BaseResponse<Boolean> updateEquipmentBorrowSpecifiedDate(@RequestBody UpdateEquipmentBorrowSpecifiedDateRequest updateEquipmentBorrowSpecifiedDateRequest, HttpServletRequest request){
        Boolean result = equipmentBorrowService.updateEquipmentBorrowSpecifiedDate(updateEquipmentBorrowSpecifiedDateRequest,request);
        return ResultUtils.success(result);
    }
}
