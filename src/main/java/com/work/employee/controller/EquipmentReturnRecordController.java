package com.work.employee.controller;

import com.work.employee.common.BaseResponse;
import com.work.employee.common.ErrorCode;
import com.work.employee.common.ResultUtils;
import com.work.employee.exception.BusinessException;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.request.equipment_return_record.AddEquipmentReturnRecordRequest;
import com.work.employee.service.EquipmentReturnRecordService;
import com.work.employee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author:HWQ
 * @DateTime:2023/9/13 0:02
 * @Description:
 **/
@RestController
@RequestMapping("/equipmentReturnRecord")
@Slf4j
public class EquipmentReturnRecordController {
    @Resource
    private EquipmentReturnRecordService equipmentReturnRecordService;
    @Resource
    private UserService userService;

    /**
     * 教师归还审核
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addEquipmentReturnRecord(@RequestBody AddEquipmentReturnRecordRequest addEquipmentReturnRecordRequest, HttpServletRequest request){
        if(addEquipmentReturnRecordRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数错误");
        }
        User loginUser = userService.getLoginUser(request);
        Boolean res = equipmentReturnRecordService.addEquipmentReturnRecord(addEquipmentReturnRecordRequest, loginUser);
        //返回请求结果
        return ResultUtils.success(res);
    }

}
