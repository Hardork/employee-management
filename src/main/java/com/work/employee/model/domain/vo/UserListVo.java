package com.work.employee.model.domain.vo;

import com.work.employee.model.domain.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @Author:HWQ
 * @DateTime:2023/6/25 17:10
 * @Description:
 **/
@Data
public class UserListVo {
    private long pageNo;
    private long pageSize;
    private long totalCount;
    private long totalPage;
    private List<User> data;
}
