package com.work.employee.model.domain.request;

import lombok.Data;

/**
 * @Author:HWQ
 * @DateTime:2023/6/29 23:44
 * @Description:
 **/
@Data
public class AddUserTaskRequest {

    /**
     * 任务标题
     */
    private String title;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 发布任务人名称
     */
    private String owner;

    /**
     * 任务进度(0-100)
     */
    private Integer progress;
}
