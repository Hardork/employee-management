package com.work.employee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.work.employee.model.domain.entity.EquipmentBorrow;
import com.work.employee.model.domain.request.equipment.DeleteEquipmentRequest;
import com.work.employee.model.domain.request.equipment_borrow.*;
import com.work.employee.model.domain.vo.equipment_borrow.EquipmentBorrowVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author 洪
* @description 针对表【equipment_borrow(器材借用表)】的数据库操作Service
* @createDate 2023-09-12 14:14:05
*/
public interface EquipmentBorrowService extends IService<EquipmentBorrow> {

    /**
     * 添加一张借用记录表
     * @param addEquipmentBorrowRequest
     * @param request
     * @return
     */
    boolean addEquipmentBorrow(AddEquipmentBorrowRequest addEquipmentBorrowRequest,HttpServletRequest request);

    /**
     * 更改归还时间
     * @return
     */
     boolean updateEquipmentBorrowSpecifiedDate(UpdateEquipmentBorrowSpecifiedDateRequest updateEquipmentBorrowSpecifiedDateRequest,HttpServletRequest request);

    /**
     * 审批（通过/未通过） 未通过需要填写原因
     * @param approveEquipmentBorrowRequest
     * @param request
     * @return
     */
    boolean approveEquipmentBorrow(ApproveEquipmentBorrowRequest approveEquipmentBorrowRequest,HttpServletRequest request);

    /**
     * 获取自己全部列表
     * @return
     */
    Page<EquipmentBorrowVo> getAllEquipmentBorrowList(EquipmentBorrowListRequest equipmentBorrowListRequest, HttpServletRequest request);


    /**
     * 根据状态来获取借用列表
     * @param equipmentBorrowPartListRequest
     * @param request
     * @return
     */
    Page<EquipmentBorrowVo> getBorrowPartList(EquipmentBorrowPartListRequest equipmentBorrowPartListRequest, HttpServletRequest request);

    /**
     * 根据登录用户以及状态来获取借用列表
     * @param equipmentBorrowPartListRequest
     * @param request
     * @return
     */
    Page<EquipmentBorrowVo> getPartListByLoginUser(EquipmentBorrowPartListRequest equipmentBorrowPartListRequest, HttpServletRequest request);

    Boolean deleteEquipment(DeleteEquipmentRequest deleteEquipmentRequest);
}
