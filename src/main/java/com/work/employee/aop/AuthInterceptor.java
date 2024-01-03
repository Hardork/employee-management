package com.work.employee.aop;


import com.work.employee.common.ErrorCode;
import com.work.employee.exception.BusinessException;
import com.work.employee.annotation.AuthCheck;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.enums.UserRoleEnum;
import com.work.employee.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验 AOP
 *
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        Integer mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        //这行代码的目的是获取HTTP请求的对象，以便我们可以从中提取请求的信息或对其进行操作。
        /*
        这行代码是在Java中使用，特别是在基于Spring框架的Web应用程序中。HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        这行代码的意思是从requestAttributes对象中获取HttpServletRequest对象。
        在Spring MVC中，requestAttributes是一个接口，它提供了对其他请求相关属性的访问，
        例如请求参数，请求头，cookies等。当我们将requestAttributes强制转换为ServletRequestAttributes类型时，
        我们可以调用其getRequest()方法，该方法返回一个HttpServletRequest对象，该对象代表了HTTP请求。
         */
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 当前登录用户
        User loginUser = userService.getLoginUser(request);
        // 必须有该权限才通过
        UserRoleEnum mustUserRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        if (mustUserRoleEnum == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        UserRoleEnum userRole = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        // 如果被封号，直接拒绝
        // 如果用户权限不匹配
        if (!mustUserRoleEnum.equals(userRole)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}

