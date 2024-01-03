package com.work.employee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.employee.model.domain.entity.EquipmentReturn;
import com.work.employee.model.domain.request.equipment_return.EquipmentReturnPartListRequest;
import com.work.employee.model.domain.vo.equipment_return.EquipmentReturnVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 洪
* @description 针对表【equipment_return】的数据库操作Mapper
* @createDate 2023-09-12 14:14:11
* @Entity com.work.employee.model.domain.entity.EquipmentReturn
*/
public interface EquipmentReturnMapper extends BaseMapper<EquipmentReturn> {

    List<EquipmentReturnVo> equipmentReturnPartList(
            @Param("obj") EquipmentReturnPartListRequest equipmentReturnPartListRequest,
            @Param("start")long start, @Param("end") long end);

    List<EquipmentReturnVo> getPartOrAllList(
            @Param("obj") EquipmentReturnPartListRequest equipmentReturnPartListRequest,
            @Param("start")long start,@Param("end")long end);
}




