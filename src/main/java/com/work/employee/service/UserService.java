package com.work.employee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.request.*;
import com.work.employee.model.domain.vo.UserListVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author HWQ
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-04-20 21:08:52
*/
public interface UserService extends IService<User> {

    /**
     *  用户注释
     * @return 用户id
     */
    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @return 用户信息
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param user 用户对象
     * @return 脱敏后的用户对象
     */
    User getSafetyUser(User user);

    /**
     * 用户注销
     * @param request
     * @return
     */
    Integer userLogout(HttpServletRequest request);



    /**
     * 更新用户信息
     * @param user
     * @param loginUser
     * @return 是否跟新成功 成功1 失败0
     */
    int  updateUserInfo(UpdateUserRequest user, User loginUser);

    /**
     * 获取当前登录用户的信息
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 根据请求鉴权
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 根据用户实体鉴权
     * @param user
     * @return 是否为管理员
     */
    boolean isAdmin(User user);



    /**
     * 获取用户列表
     * @param userListParam
     * @return
     */
    UserListVo getUserList(UserListRequest userListParam);

    /**
     * 更新我的信息
     * @param updateMyRequest
     * @param loginUser
     * @return
     */
    Integer updateMyInfo(UpdateMyRequest updateMyRequest, User loginUser);

    /**
     * 发送手机验证码
     * @param phone 手机号
     * @return
     */
    String sendCode(String phone);

    /**
     * 更新用户头像
     * @param userAvatarRequest
     * @param loginUser
     * @return
     */
    String updateUserAvatar(UpdateUserAvatarRequest userAvatarRequest, User loginUser);
}
