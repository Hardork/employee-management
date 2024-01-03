package com.work.employee.controller;

import com.work.employee.annotation.AuthCheck;
import com.work.employee.common.BaseResponse;
import com.work.employee.common.ErrorCode;
import com.work.employee.common.ResultUtils;
import com.work.employee.constant.UserConstant;
import com.work.employee.exception.ThrowUtils;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.request.equipment.AddEquipmentRequest;
import com.work.employee.model.domain.request.equipment.DeleteEquipmentRequest;
import com.work.employee.model.domain.request.equipment.EquipmentListRequest;
import com.work.employee.model.domain.request.equipment.UpdateEquipmentRequest;
import com.work.employee.model.domain.vo.equipment.EquipmentListVO;
import com.work.employee.service.EquipmentBorrowService;
import com.work.employee.service.EquipmentService;
import com.work.employee.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author:WGX
 * @DateTime:2023/9/13 13:02
 * @Description:
 **/
@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private UserService userService;

    @Resource
    private EquipmentBorrowService equipmentBorrowService;
    //添加器材
    //身份校验
    @AuthCheck(mustRole = UserConstant.TEACHER)
    @PostMapping("/add")
    public BaseResponse<Boolean> addEquipment(@RequestBody AddEquipmentRequest addEquipmentRequest, HttpServletRequest request){
        // 数据非法
        ThrowUtils.throwIf(addEquipmentRequest == null, ErrorCode.PARAMS_ERROR);
        // 获取用户的信息
        User loginUser = userService.getLoginUser(request);
        // 业务的代码
        Boolean isAdd = equipmentService.addEquipment(addEquipmentRequest, loginUser);
        return ResultUtils.success(isAdd);
    }

    @GetMapping("/list")
    public BaseResponse<EquipmentListVO> getEquipmentList(EquipmentListRequest equipmentListRequest){
        EquipmentListVO equipmentListVO = equipmentService.getEquipmentList(equipmentListRequest);
        return ResultUtils.success(equipmentListVO);
    }

    /**
     * 删除器材信息
     * @param deleteEquipmentRequest
     * @return
     */
    @AuthCheck(mustRole = UserConstant.TEACHER)
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteEquipment(@RequestBody DeleteEquipmentRequest deleteEquipmentRequest){
        Boolean result = equipmentBorrowService.deleteEquipment(deleteEquipmentRequest);
        return ResultUtils.success(result);
    }

    /**
     * 更新器材信息
     * @param updateEquipmentRequest
     * @return
     */
    @AuthCheck(mustRole = UserConstant.TEACHER)
    @PostMapping("/update")
    public BaseResponse<Boolean> updateEquipment(@RequestBody UpdateEquipmentRequest updateEquipmentRequest){
        Boolean result = equipmentService.updateEquipment(updateEquipmentRequest);
        return ResultUtils.success(result);
    }
}
