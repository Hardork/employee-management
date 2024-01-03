package com.work.employee.model.domain.request;

import lombok.Data;

/**
 * @Author:HWQ
 * @DateTime:2023/6/29 23:57
 * @Description:
 **/
@Data
public class UpdateUserTaskRequest {
    private Integer id;
    private String title;
    private Integer progress;
    private String owner;
    private String description;
}
