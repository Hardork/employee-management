package com.work.employee.model.domain.request;

import lombok.Data;

/**
 * @Author:HWQ
 * @DateTime:2023/7/2 22:54
 * @Description:
 **/
@Data
public class UpdateUserAvatarRequest {
    private Long id;
    private String base64Data;
}
