package com.work.employee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.work.employee.annotation.AuthCheck;
import com.work.employee.common.BaseResponse;
import com.work.employee.common.ErrorCode;
import com.work.employee.common.PageResult;
import com.work.employee.common.ResultUtils;
import com.work.employee.constant.UserConstant;
import com.work.employee.exception.BusinessException;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.request.equipment_return.AddEquipmentReturnRequest;
import com.work.employee.model.domain.request.equipment_return.EquipmentReturnPartListRequest;
import com.work.employee.model.domain.vo.equipment_return.EquipmentReturnVo;
import com.work.employee.service.EquipmentReturnService;
import com.work.employee.service.UserService;
import com.work.employee.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author:HWQ
 * @DateTime:2023/9/12 19:38
 * @Description:
 **/
@RestController
@RequestMapping("/equipmentReturn")
@Slf4j
public class EquipmentReturnController {

    @Resource
    private EquipmentReturnService equipmentReturnService;
    @Resource
    private UserService userService;

    /**
     * 用户发起器材归还请求
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addEquipmentReturn(@RequestBody AddEquipmentReturnRequest addEquipmentReturnRequest, HttpServletRequest request){
        if(addEquipmentReturnRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数错误");
        }
        User loginUser = userService.getLoginUser(request);
        Boolean res = equipmentReturnService.addEquipmentReturn(addEquipmentReturnRequest, loginUser);
        //返回请求结果
        return ResultUtils.success(res);
    }

    /**
     * 根据条件查询对应的归还表内容
     */
    @AuthCheck(mustRole = UserConstant.TEACHER)
    @PostMapping("/list/page/equipmentReturn")
    public BaseResponse<PageResult<EquipmentReturnVo>> equipmentReturnPartList(
            @RequestBody EquipmentReturnPartListRequest equipmentReturnPartListRequest,
            HttpServletRequest request){
        if (equipmentReturnPartListRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        Page<EquipmentReturnVo> res = equipmentReturnService.equipmentReturnPartList(equipmentReturnPartListRequest);
        PageResult<EquipmentReturnVo> pageResult = PageUtils.getPageResult(res);
        //返回请求结果
        return ResultUtils.success(pageResult);
    }

    /**
     * 用户查询自己的订单
     * @param equipmentReturnPartListRequest
     * @param request
     * @return
     */
    @PostMapping("/getPartOrAllList")
    public BaseResponse<PageResult<EquipmentReturnVo>> getPartOrAllList(
            @RequestBody EquipmentReturnPartListRequest equipmentReturnPartListRequest,
            HttpServletRequest request){
        Page<EquipmentReturnVo> res = equipmentReturnService.getPartOrAllList(equipmentReturnPartListRequest,request);
        PageResult<EquipmentReturnVo> pageResult = PageUtils.getPageResult(res);
        return ResultUtils.success(pageResult);
    }

//    @PostMapping("/loss")
//    public BaseResponse<Boolean> equipmentLoss(@RequestBody EquipmentLossRequest equipmentLossRequest,HttpServletRequest request){
//        if(equipmentLossRequest == null){
//            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数错误");
//        }
//        User loginUser = userService.getLoginUser(request);
//        Boolean res = equipmentReturnService.equipmentLoss(equipmentLossRequest, loginUser);
//        //返回请求结果
//        return ResultUtils.success(res);
//    }
}
