package com.work.employee.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.work.employee.common.ErrorCode;
import com.work.employee.constant.UserConstant;
import com.work.employee.exception.BusinessException;
import com.work.employee.mapper.UserMapper;
import com.work.employee.model.domain.entity.Personnel;
import com.work.employee.model.domain.entity.User;
import com.work.employee.model.domain.enums.UserRoleEnum;
import com.work.employee.model.domain.request.*;
import com.work.employee.service.PersonnelService;
import com.work.employee.service.UserService;
import com.work.employee.utils.UpdateUserAvatarUrl;
import com.work.employee.model.domain.vo.UserListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
* @author HWQ
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-04-20 21:08:52
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UpdateUserAvatarUrl updateUserAvatarUrl;
    @Resource
    private PersonnelService personnelService;
    /**
     * 盐值，混淆密码
     */
    final String SALT = "sport_system_background";
    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest) {
        String userPassword = userRegisterRequest.getUserPassword();
        String userAccount = userRegisterRequest.getUserAccount();
        String checkPassword = userRegisterRequest.getCheckPassword();
        //非空校验
        if(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
            //todo；异常
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        //校验账户长度
        if(userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账户过短");
        }
        //校验密码
        if(userPassword.length() < 8 || checkPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }
        //账户不能包含特定字符
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(userAccount);
        if(m.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户不能包含特定字符");
        }
        //密码与校验密码相同
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码与校验密码不同");
        }
        // 账户重复
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("userAccount",userAccount);
        long count = userMapper.selectCount(qw);
        if(count > 0 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户重复");
        }
        // 去personnel表中查询有没有这个学生或教师
        QueryWrapper<Personnel> personnelQueryWrapper = new QueryWrapper<>();
        personnelQueryWrapper.eq("personnelNo", userAccount);
        Personnel personnelInfo = personnelService.getOne(personnelQueryWrapper);
        if (personnelInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不存在该人员");
        }
        // 获取该人员的身份
        Integer personnelRole = personnelInfo.getPersonnelRole();
        UserRoleEnum enumByValue = UserRoleEnum.getEnumByValue(personnelRole);
        // 身份未知
        if (enumByValue == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        //密码加密
        String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        user.setUsername(userAccount);
        user.setUserRole(enumByValue.getValue());
        boolean saveResult = this.save(user);
        if(!saveResult){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"插入错误");
        }
        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.非空校验
        if(StringUtils.isAnyBlank(userAccount, userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户或密码不能为空");
        }
        //2.校验账户长度
        if(userAccount.length() < 4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户长度错误");
        }
        //3.校验密码
        if(userPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度错误");
        }
        //4.账户不能包含特定字符
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(userAccount);
        if(m.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户不能包含特殊字符");
        }
        //密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        //查询用户是否存在
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("userAccount",userAccount);
        qw.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(qw);
        if(user == null){
            log.info("user login failed, userAccount can not match");
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR,"账户不存在");
        }
        //3.用户脱敏
        User safetyUser = getSafetyUser(user);
        //4.记录用户的登录态,用于下一次用户登录，判断是否已经登录过
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, safetyUser);
        //返回信息
        return safetyUser;
    }

    /**
     * 用户脱敏
     * @param user 原来的用户对象
     * @return 经过脱敏处理的用户对象
     */
    public User getSafetyUser(User user){
        if(user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户重复");
        }
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        return safetyUser;
    }

    /**
     * 用户注销
     * @param request 用于获取session
     */
    @Override
    public Integer userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return 1;
    }


    /**
     *
     * @param user 更新后的用户信息
     * @param loginUser 当前登录用户的信息
     * @return
     */
    @Override
    public int updateUserInfo(UpdateUserRequest user, User loginUser) {
        //判断是否传了id
        long userId = user.getId();
        if(userId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //用户自己，只允许更新自己的信息
        //管理员，可以更新所有用户信息
        if(!isAdmin(loginUser) && loginUser.getId() != userId){//非管理员，并且要更新的用户不是自己
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //符合更新条件
        User oldUser = userMapper.selectById(userId);
        if(oldUser == null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        oldUser.setUsername(user.getUsername());

        return userMapper.updateById(oldUser);
    }


    /**
     * 获取当前登录用户的信息
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        if(request == null){
            return null;
        }
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if(userObj == null){
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        return (User) userObj;
    }

    /**
     * 鉴权
     * @param request
     * @return 是否为管理员
     */
    @Override
    public boolean isAdmin(HttpServletRequest request){
        //鉴权，仅管理员可操作
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if(user == null){
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        //不是管理员
        return user.getUserRole() == UserConstant.ADMIN_ROLE;//空数组
    }

    @Override
    public boolean isAdmin(User user){
        //鉴权，仅管理员可操作
        //不是管理员
        return user != null && user.getUserRole() == UserConstant.TEACHER;//空数组
    }


    @Override
    public UserListVo getUserList(UserListRequest userListParam) {
        if(userListParam == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //拿出请求对象中的值进行校验
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        Integer userRole = userListParam.getUserRole();
        Date createTime = userListParam.getCreateTime();
        String userAccount = userListParam.getUserAccount();
        String username = userListParam.getUsername();
        if (!StringUtils.isEmpty(username)) {
            userQueryWrapper.eq("username", username);
        }
        if(userRole != null){
            userQueryWrapper.eq("userRole",userRole);
        }
        if(userAccount != null){
            userQueryWrapper.eq("userAccount",userAccount);
        }
        if(createTime != null){
            userQueryWrapper.eq("date_format(createTime, '%Y-%m-%d')", String.format(createTime.toString(), "yyyy-MM-dd"));
        }
        //去数据库获取分页数据
        long pageNo = userListParam.getPageNo();
        long pageSize = userListParam.getPageSize();
        //缓存中没数据，从数据库中获取，并写入到缓存中
        Page<User> userPage = this.page(new Page<>(pageNo,pageSize),userQueryWrapper);
        List<User> userList = userPage.getRecords();
        //返回安全的用户数据
        List<User> safetyUserList = userList.stream().map(this::getSafetyUser).collect(Collectors.toList());
        UserListVo userListVo = new UserListVo();
        userListVo.setTotalCount(userPage.getTotal());
        userListVo.setPageNo(userPage.getCurrent());
        userListVo.setPageSize(userPage.getSize());
        userListVo.setTotalPage(userPage.getPages());
        userListVo.setData(safetyUserList);
        return userListVo;
    }

    @Override
    public Integer updateMyInfo(UpdateMyRequest updateMyRequest, User loginUser) {
        //校验数据
        String username = updateMyRequest.getUsername();
        String email = updateMyRequest.getEmail();
        String profile = updateMyRequest.getProfile();
        String phone = updateMyRequest.getPhone();
        Long userId = loginUser.getId();
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", userId);
        boolean needUpdate = false;
        if(!StringUtils.isBlank(username)){
            userUpdateWrapper.set("username",username);
            needUpdate = true;
        }
        if(!StringUtils.isBlank(profile)){
            userUpdateWrapper.set("profile",profile);
            needUpdate = true;
        }
        if(!StringUtils.isBlank(email)){
            userUpdateWrapper.set("email",email);
            needUpdate = true;
        }
        if(!StringUtils.isBlank(phone)){
            userUpdateWrapper.set("phone",phone);
            needUpdate = true;
        }
        if(!needUpdate){
            //不需要更新
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //符合更新条件
        User oldUser = userMapper.selectById(userId);
        if(oldUser == null){
            throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
        }
        boolean update = this.update(userUpdateWrapper);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return 1;
    }

    @Override
    public String sendCode(String phone) {
        // 生成4位手机验证码code
        String code = RandomUtil.randomNumbers(4);
        return code;
    }

    @Override
    public String updateUserAvatar(UpdateUserAvatarRequest userAvatarRequest, User loginUser) {
        Long userId = userAvatarRequest.getId();
        if(userId == null || userId <= 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String base64Data = userAvatarRequest.getBase64Data();
        String fileName = updateUserAvatarUrl.uploadBase64File(base64Data);
        // 将用户的头像地址存入数据库
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", userId).set("avatarUrl", fileName);
        boolean update = this.update(userUpdateWrapper);
        if(!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return fileName;
    }
}




