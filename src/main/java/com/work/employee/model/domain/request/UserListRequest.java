package com.work.employee.model.domain.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:HWQ
 * @DateTime:2023/6/25 17:06
 * @Description: 请求用户列表实体
 **/
@Data
public class UserListRequest implements Serializable {
    private static final long serialVersionUID = 7851750037521868036L;
    private long pageNo;
    private long pageSize;
    private Integer userRole;
    private String username;
    private String userAccount;
    private Date createTime;
}
