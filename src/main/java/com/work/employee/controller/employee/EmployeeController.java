package com.work.employee.controller.employee;

import com.work.employee.common.BaseResponse;
import com.work.employee.common.ResultUtils;
import com.work.employee.model.domain.request.employee.EmployeeListRequest;
import com.work.employee.model.domain.vo.employee.EmployeeListVO;
import com.work.employee.service.DeptEmpDetailService;
import com.work.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author:HWQ
 * @DateTime:2024/1/1 14:41
 * @Description: 员工
 **/
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @Resource
    private DeptEmpDetailService deptEmpDetailService;


    @GetMapping("/list")
    public BaseResponse<EmployeeListVO> getEmployeeList(EmployeeListRequest employeeListRequest){
        EmployeeListVO employeeListVO = deptEmpDetailService.getEmployeeList(employeeListRequest);
        return ResultUtils.success(employeeListVO);
    }

}