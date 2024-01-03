package com.work.employee.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.work.employee.common.PageResult;

/**
 * @Author:HCJ
 * @DateTime:2023/9/14
 * @Description:
 **/
public class PageUtils {
    public static <T> PageResult<T>  getPageResult(Page<T> data){
        PageResult<T> result = new PageResult<T>();
        result.setPageNo(data.getCurrent());
        result.setPageSize(data.getSize());
        result.setTotalCount(data.getTotal());
        result.setTotalPage(data.getPages());
        result.setData(data.getRecords());
        return result;
    }
}
