package com.work.employee.exception;

import com.work.employee.common.BaseResponse;
import com.work.employee.common.ErrorCode;
import com.work.employee.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author:HWQ
 * @DateTime:2023/4/22 23:16
 * @Description: 全局对特定异常捕获
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获BusinessException类型的异常,进行处理,避免后端将异常直接抛给前端
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.error("businessException" + e.getMessage(), e);
        return ResultUtils.error(e.getCode(),e.getMessage(),e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse RuntimeExceptionHandler(BusinessException e){
        log.error("runtimeException",e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
    }
}
