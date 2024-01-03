package com.work.employee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.work.employee.model.domain.entity.EquipmentBorrowRecord;
import com.work.employee.model.domain.request.equipment_borrow_record.AllListRequest;
import com.work.employee.model.domain.request.equipment_borrow_record.PersonnelNoListRequest;

import javax.servlet.http.HttpServletRequest;

/**
* @author 洪
* @description 针对表【equipment_borrow_record(器材借用记录表)】的数据库操作Service
* @createDate 2023-09-12 14:14:08
*/
public interface EquipmentBorrowRecordService extends IService<EquipmentBorrowRecord> {

    /**
     * 添加一条记录
     * @param equipmentBorrowRecord
     * @return
     */
    boolean addRecord(EquipmentBorrowRecord equipmentBorrowRecord);

    /**
     * 查询自己的借出所有记录
     * @param request
     * @return
     */
    Page<EquipmentBorrowRecord> getMyList(AllListRequest allListRequest, HttpServletRequest request);

    /**
     * 管理员根据人员编号去查询对应的记录
     * @param personnelNoListRequest 人员编号
     * @param request
     * @return
     */
    Page<EquipmentBorrowRecord> getListByPersonnelNo(PersonnelNoListRequest personnelNoListRequest, HttpServletRequest request);

    /**
     * 获取一条记录的详细情况
     * @param borrowRecordID 查询记录的id
     * @param request
     * @return
     */
    EquipmentBorrowRecord getDetails(String borrowRecordID, HttpServletRequest request);

    /**
     * 管理员查询所有记录
     * @param request
     * @return
     */
    Page<EquipmentBorrowRecord> getAllList(AllListRequest allListRequest,HttpServletRequest request);
}
