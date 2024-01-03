package com.work.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.common.ErrorCode;
import com.work.employee.exception.BusinessException;
import com.work.employee.exception.ThrowUtils;
import com.work.employee.mapper.EquipmentMapper;
import com.work.employee.model.domain.entity.Equipment;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.enums.EquipmentStatusEnum;
import com.work.employee.model.domain.enums.UserRoleEnum;
import com.work.employee.model.domain.request.equipment.AddEquipmentRequest;
import com.work.employee.model.domain.request.equipment.DeleteEquipmentRequest;
import com.work.employee.model.domain.request.equipment.EquipmentListRequest;
import com.work.employee.model.domain.request.equipment.UpdateEquipmentRequest;
import com.work.employee.model.domain.vo.equipment.EquipmentListVO;
import com.work.employee.service.EquipmentService;
import com.work.employee.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 洪
* @description 针对表【equipment(器材表)】的数据库操作Service实现
* @createDate 2023-09-12 14:13:58
*/
@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment>
    implements EquipmentService {

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private UserService userService;
    

    @Override
    public Equipment getByNo(String equipmentNo) {
        QueryWrapper<Equipment> wrapper = new QueryWrapper<>();
        wrapper.eq("equipmentNo",equipmentNo);
        return equipmentMapper.selectOne(wrapper);
    }

    @Override
    public boolean addStock(String equipmentNo, Integer leftNum) {
        //参数校验
        if(StringUtils.isBlank(equipmentNo)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
        if(leftNum==null || leftNum<0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
        // 查询库存
        QueryWrapper<Equipment> qw = new QueryWrapper<>();
        qw.eq("equipmentNo",equipmentNo);
        Equipment equipment = getOne(qw);
        if(equipment ==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数错误");
        }
        //修改库存
        UpdateWrapper<Equipment> uw = new UpdateWrapper<>();
        uw.set("leftNum",equipment.getLeftNum()+leftNum).eq("equipmentNo",equipmentNo);
        return update(uw);
    }

    @Override
    public EquipmentListVO getEquipmentList(EquipmentListRequest equipmentListRequest) {
        if(equipmentListRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //拿出请求对象中的值进行校验
        QueryWrapper<Equipment> equipmentQueryWrapper = new QueryWrapper<>();

        String equipmentNo = equipmentListRequest.getEquipmentNo();
        String equipmentName = equipmentListRequest.getEquipmentName();
        String equipmentModel = equipmentListRequest.getEquipmentModel();
        if (!StringUtils.isEmpty(equipmentNo)) {
            equipmentQueryWrapper.eq("equipmentNo", equipmentNo);
        }

        if (!StringUtils.isEmpty(equipmentName)) {
            equipmentQueryWrapper.eq("equipmentName", equipmentName);
        }

        if (!StringUtils.isEmpty(equipmentModel)) {
            equipmentQueryWrapper.eq("equipmentModel", equipmentModel);
        }
        //去数据库获取分页数据
        long pageNo = equipmentListRequest.getPageNo();
        long pageSize = equipmentListRequest.getPageSize();
        //缓存中没数据，从数据库中获取，并写入到缓存中
        Page<Equipment> equipmentPage = this.page(new Page<>(pageNo,pageSize),equipmentQueryWrapper);
        List<Equipment> equipmentList = equipmentPage.getRecords();
        //返回安全的用户数据
        List<Equipment> safetyEquipmentList = equipmentList.stream().
                map(this::getSafetyEquipmentInfo).collect(Collectors.toList());
        EquipmentListVO equipmentListVO = new EquipmentListVO();
        equipmentListVO.setPageNo(equipmentPage.getCurrent());
        equipmentListVO.setPageSize(equipmentPage.getSize());
        equipmentListVO.setTotalCount(equipmentPage.getTotal());
        equipmentListVO.setTotalPage(equipmentPage.getPages());
        equipmentListVO.setData(safetyEquipmentList);
        return equipmentListVO;
    }

    @Override
    public Boolean addEquipment(AddEquipmentRequest addEquipmentRequest, User loginUser) {
        // 获取所有用户提交的参数
        String personnelNo = loginUser.getUserAccount(); // 添加人
        String equipmentNo = addEquipmentRequest.getEquipmentNo(); // 设备编号
         String equipmentName = addEquipmentRequest.getEquipmentName(); // 设备名字
         String equipmentModel = addEquipmentRequest.getEquipmentModel(); // 设备型号
         Double equipmentPrice = addEquipmentRequest.getEquipmentPrice(); // 设备价格
         Integer leftNum = addEquipmentRequest.getLeftNum(); // 剩余的数量
         Date purchaseDate = addEquipmentRequest.getPurchaseDate(); // 购买日期
        // 校验参数
         ThrowUtils.throwIf(StringUtils.isEmpty(personnelNo), ErrorCode.PARAMS_ERROR, "添加人id不能为空");
         ThrowUtils.throwIf(StringUtils.isEmpty(equipmentNo), ErrorCode.PARAMS_ERROR, "设备编号不能为空");
         ThrowUtils.throwIf(StringUtils.isEmpty(equipmentName), ErrorCode.PARAMS_ERROR, "设备名称不能为空");
         ThrowUtils.throwIf(StringUtils.isEmpty(equipmentModel), ErrorCode.PARAMS_ERROR, "设备型号不能为空");
         ThrowUtils.throwIf(equipmentPrice==null,ErrorCode.PARAMS_ERROR,"设备价格不能为空");
         ThrowUtils.throwIf(leftNum==null,ErrorCode.PARAMS_ERROR,"添加设备数量不能为空");
         ThrowUtils.throwIf(purchaseDate==null,ErrorCode.PARAMS_ERROR,"购买日期不能为空");
        // 查询有没有重复的编号
        QueryWrapper<Equipment> equipmentQueryWrapper = new QueryWrapper<>();
        equipmentQueryWrapper.eq("equipmentNo", equipmentNo);
        Equipment hasSameEquipment = this.getOne(equipmentQueryWrapper);
        if (hasSameEquipment != null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "器材编号已存在");
        }
        // 查询添加器材的用户人权限
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", personnelNo);
        User user = userService.getOne(userQueryWrapper);
        ThrowUtils.throwIf(user == null, ErrorCode.NO_LOGIN);
        UserRoleEnum enumByValue = UserRoleEnum.getEnumByValue(user.getUserRole());
        ThrowUtils.throwIf(enumByValue != UserRoleEnum.TEACHER, ErrorCode.NO_AUTH);

        // 添加设备
         Equipment equipment = new Equipment();
         BeanUtils.copyProperties(addEquipmentRequest,equipment);
         // 默认可用
         equipment.setStatus(EquipmentStatusEnum.AVAILABLE.getValue());
         equipment.setEquipmentNo(equipmentNo);
         equipment.setPersonnelNo(personnelNo);
        // 保存到数据库
         boolean result = save(equipment);
         if(!result){
              throw new BusinessException(ErrorCode.SYSTEM_ERROR,"器材报错失败");
         }
         return true;
    }

    //从库存中去掉器材
    @Override
    public Boolean deleteEquipment(DeleteEquipmentRequest deleteEquipmentRequest) {
        ThrowUtils.throwIf(deleteEquipmentRequest==null,ErrorCode.PARAMS_ERROR,"参数不能为空");
        Long equipmentID = deleteEquipmentRequest.getEquipmentID();
        ThrowUtils.throwIf( equipmentID == null,
                ErrorCode.PARAMS_ERROR,"参数不能为空");

        QueryWrapper<Equipment> qw = new QueryWrapper<>();
        qw.eq("equipmentID", equipmentID);



        boolean result = remove(qw);
        if(!result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除器材失败");
        }
        return true;
    }

    @Override
    public Boolean updateEquipment(UpdateEquipmentRequest updateEquipmentRequest) {
        // 参数校验
        ThrowUtils.throwIf(updateEquipmentRequest==null, ErrorCode.PARAMS_ERROR,"参数不能为空");

        // 1.1 验证器材id
        ThrowUtils.throwIf(updateEquipmentRequest.getEquipmentID()==null,
                ErrorCode.PARAMS_ERROR,"器材id不能为空");
        // 1.3 验证编号
        ThrowUtils.throwIf(StringUtils.isBlank(updateEquipmentRequest.getEquipmentNo()),
                ErrorCode.PARAMS_ERROR,"器材编号不能为空");
        // 1.4 验证器材名字
        ThrowUtils.throwIf(StringUtils.isBlank(updateEquipmentRequest.getEquipmentName()),
                ErrorCode.PARAMS_ERROR,"器材名字不能为空");
        // 1.5 验证器材型号
        ThrowUtils.throwIf(StringUtils.isBlank(updateEquipmentRequest.getEquipmentModel()),
                ErrorCode.PARAMS_ERROR,"器材型号不能为空");
        // 1.6 验证器材价格
        Double equipmentPrice = updateEquipmentRequest.getEquipmentPrice();
        ThrowUtils.throwIf(equipmentPrice==null || equipmentPrice<0,
                ErrorCode.PARAMS_ERROR,"器材价格不能为空");
        // 1.7 验证器材库存
        Integer leftNum = updateEquipmentRequest.getLeftNum();
        ThrowUtils.throwIf(leftNum==null || leftNum<0,
                ErrorCode.PARAMS_ERROR,"器材库存不能为空");
        // 查
        QueryWrapper<Equipment> equipmentQueryWrapper = new QueryWrapper<>();
        equipmentQueryWrapper.eq("equipmentID", updateEquipmentRequest.getEquipmentID());
        Equipment equipment = this.getOne(equipmentQueryWrapper);
        // 1.10 验证状态
        Integer status = updateEquipmentRequest.getStatus();
        ThrowUtils.throwIf(status==null,ErrorCode.PARAMS_ERROR,"器材状态不能为空");
        EquipmentStatusEnum enumByValue = EquipmentStatusEnum.getEnumByValue(status);
        ThrowUtils.throwIf(enumByValue == null,
                ErrorCode.PARAMS_ERROR,"该状态不存在");
        BeanUtils.copyProperties(updateEquipmentRequest,equipment);
        boolean result = updateById(equipment);
        if(!result){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return true;
    }

    @Override
    public Equipment getEquipmentOne(String equipmentNo) {
        QueryWrapper<Equipment> qw = new QueryWrapper<>();
        qw.eq("equipmentNo",equipmentNo);
        return equipmentMapper.selectOne(qw);
    }

    public Equipment getSafetyEquipmentInfo(Equipment equipment){
        if(equipment == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Equipment safetyEquipment = new Equipment();
        safetyEquipment.setEquipmentNo(equipment.getEquipmentNo());
        safetyEquipment.setEquipmentName(equipment.getEquipmentName());
        safetyEquipment.setEquipmentID(equipment.getEquipmentID());
        safetyEquipment.setEquipmentPrice(equipment.getEquipmentPrice());
        safetyEquipment.setEquipmentModel(equipment.getEquipmentModel());
        safetyEquipment.setLeftNum(equipment.getLeftNum());
        safetyEquipment.setStatus(equipment.getStatus());
        return safetyEquipment;
    }
}




