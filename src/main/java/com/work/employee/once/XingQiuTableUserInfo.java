package com.work.employee.once;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**

 **/
@Data
public class XingQiuTableUserInfo {
    @ExcelProperty("成员编号")
    private String planetCode;

    /**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     */
    @ExcelProperty("成员昵称")
    private String username;
}

