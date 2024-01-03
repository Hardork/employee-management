package com.work.employee.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.work.employee.annotation.AuthCheck;
import com.work.employee.common.BaseResponse;
import com.work.employee.common.DeleteRequest;
import com.work.employee.common.ErrorCode;
import com.work.employee.common.ResultUtils;
import com.work.employee.constant.UserConstant;
import com.work.employee.exception.BusinessException;
import com.work.employee.exception.ThrowUtils;
import com.work.employee.model.domain.entity.EquipmentBorrow;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.request.*;
import com.work.employee.model.domain.vo.UserListVo;
import com.work.employee.service.EquipmentBorrowService;
import com.work.employee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: HWQ
 * @DateTime:2023/4/21 14:13
 * @Description: 控制层接口
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private EquipmentBorrowService equipmentBorrowService;

    /**
     * 注册用户接口
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数错误");
        }
        long result = userService.userRegister(userRegisterRequest);
        //返回请求结果
        return ResultUtils.success(result);
    }

    /**
     * 登录接口
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userPassword = userLoginRequest.getUserPassword();
        String userAccount = userLoginRequest.getUserAccount();
        if(StringUtils.isAnyBlank(userAccount, userPassword)){
            //todo；异常
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.doLogin(userAccount, userPassword, request);
        //返回请求结果
        return ResultUtils.success(user);
    }

    /**
     * 注销接口
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if(request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer flag = userService.userLogout(request);
        return ResultUtils.success(flag);
    }

    /**
     * 获取用户列表
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request, HttpServletResponse response){
        //todo 校验用户是否合法
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getSafetyUser(loginUser));
    }

    /**
     * 获取用户列表（myself）
     * @param request
     * @return
     */
    @GetMapping("/list")
    public BaseResponse<UserListVo> getUserList(UserListRequest userListParam, HttpServletRequest request){
        UserListVo userListVo = userService.getUserList(userListParam);
        return ResultUtils.success(userListVo);
    }

    /**
     * 搜索用户接口
     * @param username
     * @param request
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username, HttpServletRequest request){
        //鉴权，仅管理员可查询
        if(!userService.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        QueryWrapper<User> qw = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){//username不为空
            qw.like("username",username);
        }
        List<User> userList = userService.list(qw);
        List<User> list = userList.stream().map(user -> {
            user.setUserPassword(null);
            return user;
        }).collect(Collectors.toList());
        // 返回经过处理(将密码隐藏)的List
        return ResultUtils.success(list);
    }




    /**
     * 更新用户的信息
     * @param updateUserRequest
     * @param request
     * @return
     */
    @GetMapping("/update")
    public BaseResponse<Integer> updateUserInfo(UpdateUserRequest updateUserRequest, HttpServletRequest request){
        //1.校验参数是否为空
        if(updateUserRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //判断要更新的id是否为空
        if(updateUserRequest.getId() == null || updateUserRequest.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //2.校验权限
        User loginUser = userService.getLoginUser(request);

        //3，触发更新
        Integer result = userService.updateUserInfo(updateUserRequest, loginUser);
        return ResultUtils.success(result);
    }


    /**
     * 删除用户接口
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.TEACHER)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request){
        if(deleteRequest.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        // 判断用户有没有订单
        User user = userService.getById(deleteRequest.getId());
        String userAccount = user.getUserAccount();
        QueryWrapper<EquipmentBorrow> equipmentBorrowQueryWrapper = new QueryWrapper<>();
        equipmentBorrowQueryWrapper.eq("borrowPersonnelNo", userAccount);
        List<EquipmentBorrow> list = equipmentBorrowService.list(equipmentBorrowQueryWrapper);
        ThrowUtils.throwIf(list.size() > 0, ErrorCode.PARAMS_ERROR, "用户还有订单未结束");
        //这里的删除是逻辑删除
        return ResultUtils.success(userService.removeById(deleteRequest.getId()));
    }
    }
