package com.work.employee.model.domain.request.employee;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:HWQ
 * @DateTime:2023/9/13 13:04
 * @Description:
 **/
@Data
public class EmployeeListRequest implements Serializable {
    private long pageNo;
    private long pageSize;
    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 根据名称查询
     */
    private String name;
}
