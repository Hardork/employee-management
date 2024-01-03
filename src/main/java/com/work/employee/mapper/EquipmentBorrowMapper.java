package com.work.employee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.employee.model.domain.entity.EquipmentBorrow;
import com.work.employee.model.domain.request.equipment_borrow.EquipmentBorrowPartListRequest;
import com.work.employee.model.domain.vo.equipment_borrow.EquipmentBorrowVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 洪
* @description 针对表【equipment_borrow(器材借用表)】的数据库操作Mapper
* @createDate 2023-09-12 14:14:05
* @Entity generator.domain.EquipmentBorrow
*/
public interface EquipmentBorrowMapper extends BaseMapper<EquipmentBorrow> {
    List<EquipmentBorrowVo> getEquipmentList(@Param("borrowPersonnelNo")String borrowPersonnelNo,@Param("start")long start, @Param("end") long end);

    List<EquipmentBorrowVo> getEquipmentPartList(
            @Param("obj") EquipmentBorrowPartListRequest equipmentBorrowPartListRequest,
            @Param("start")long start, @Param("end") long end);

    List<EquipmentBorrowVo> getPartListByLoginUser(@Param("status")Integer status,@Param("borrowPersonnelNo")String borrowPersonnelNo,@Param("start")long start, @Param("end") long end);

}




