package com.work.employee.common;

import lombok.Data;

import java.util.List;

/**
 * @Author:HCJ
 * @DateTime:2023/9/14
 * @Description:
 **/
@Data
public class PageResult<T> {
    private long pageNo;
    private long pageSize;
    private long totalCount;
    private long totalPage;
    private List<T> data;
}
