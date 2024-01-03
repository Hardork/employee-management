package com.work.employee.mapper;

import com.work.employee.model.domain.entity.AttendanceRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.work.employee.model.domain.vo.employee.GetDeptAttendanceRecordsVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author HWQ
* @description 针对表【attendance_record(考勤记录表)】的数据库操作Mapper
* @createDate 2024-01-01 14:35:29
* @Entity com.work.employee.model.domain.entity.AttendanceRecord
*/
public interface AttendanceRecordMapper extends BaseMapper<AttendanceRecord> {
    @Select("CALL getDeptAttendanceRecords(#{queryDate}, #{queryDeptID}, #{queryEmployeeID})")
    List<GetDeptAttendanceRecordsVO> getDeptAttendanceRecords(
            @Param("queryDate") Date queryDate,
            @Param("queryDeptID") Long queryDeptID,
            @Param("queryEmployeeID") Long queryEmployeeID
    );
}




