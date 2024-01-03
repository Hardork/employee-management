package com.work.employee.mapper;

import com.work.employee.model.domain.entity.WorkTicket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.employee.model.domain.vo.employee.WorkTicketVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
* @author HWQ
* @description 针对表【work_ticket(工单表)】的数据库操作Mapper
* @createDate 2024-01-01 14:35:56
* @Entity com.work.employee.model.domain.entity.WorkTicket
*/
public interface WorkTicketMapper extends BaseMapper<WorkTicket> {
    @Select("CALL getWorkTicket(#{queryDate}, #{queryDeptID}, #{queryEmployeeID})")
    List<WorkTicketVO> getWorkTicketByDept(@Param("queryDate") Date queryDate,
                                           @Param("queryDeptID") Long queryDeptID,
                                           @Param("queryEmployeeID") Long queryEmployeeID
    );
}




