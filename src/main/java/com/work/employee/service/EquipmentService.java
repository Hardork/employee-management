package com.work.employee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.employee.model.domain.entity.Equipment;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.request.equipment.AddEquipmentRequest;
import com.work.employee.model.domain.request.equipment.DeleteEquipmentRequest;
import com.work.employee.model.domain.request.equipment.EquipmentListRequest;
import com.work.employee.model.domain.request.equipment.UpdateEquipmentRequest;
import com.work.employee.model.domain.vo.equipment.EquipmentListVO;

/**
* @author 洪
* @description 针对表【equipment(器材表)】的数据库操作Service
* @createDate 2023-09-12 14:13:58
*/
public interface EquipmentService extends IService<Equipment> {

    Equipment getByNo(String equipmentNo);

    /**
     * 添加库存
     * @param equipmentNo 器材编号
     * @param leftNum 减少库存数量
     * @return
     */
    boolean addStock(String equipmentNo, Integer leftNum);

    /**
     * 获取所有器材信息
     * @param equipmentListRequest
     * @return
     */
    EquipmentListVO getEquipmentList(EquipmentListRequest equipmentListRequest);

    /**
     * 添加器材
     * @param addEquipmentRequest
     * @param loginUser
     * @return
     */
    Boolean addEquipment(AddEquipmentRequest addEquipmentRequest, User loginUser);

    /**
     * 删除器材
     * @param deleteEquipmentRequest
     * @return
     */
    Boolean deleteEquipment(DeleteEquipmentRequest deleteEquipmentRequest);

    /**
     * 修改器材信息
     * @param updateEquipmentRequest
     * @return
     */
    Boolean updateEquipment(UpdateEquipmentRequest updateEquipmentRequest);

    /**
     * 获取一条器材消息
     * @param equipmentNo
     * @return
     */
    Equipment getEquipmentOne(String equipmentNo);
}
