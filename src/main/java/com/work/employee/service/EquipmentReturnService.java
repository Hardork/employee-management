package com.work.employee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.work.employee.model.domain.entity.EquipmentReturn;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.request.equipment_return.AddEquipmentReturnRequest;
import com.work.employee.model.domain.request.equipment_return.EquipmentReturnPartListRequest;
import com.work.employee.model.domain.vo.equipment_return.EquipmentReturnVo;

import javax.servlet.http.HttpServletRequest;


/**
* @author 洪
* @description 针对表【equipment_return】的数据库操作Service
* @createDate 2023-09-12 14:14:11
*/
public interface EquipmentReturnService extends IService<EquipmentReturn> {

    /**
     * 发起归还请求
     * @param addEquipmentReturnRequest
     * @param loginUser
     * @return
     */
    Boolean addEquipmentReturn(AddEquipmentReturnRequest addEquipmentReturnRequest, User loginUser);

    /**
     * 获取待审批的归还请求
     * @param equipmentReturnPartListRequest
     * @return
     */
    Page<EquipmentReturnVo> equipmentReturnPartList(EquipmentReturnPartListRequest equipmentReturnPartListRequest);

    /**
     * 获取个人所有的审批记录
     * @param request
     * @return
     */
    Page<EquipmentReturnVo> getPartOrAllList(EquipmentReturnPartListRequest equipmentReturnPartListRequest,HttpServletRequest request);

    /**
     *设备报失
     * @return
     */
//    Boolean equipmentLoss(EquipmentLossRequest equipmentLossRequest, User loginUser);
}
