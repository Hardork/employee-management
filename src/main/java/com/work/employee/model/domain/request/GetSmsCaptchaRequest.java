package com.work.employee.model.domain.request;

import lombok.Data;

/**
 * @Author:HWQ
 * @DateTime:2023/6/30 16:37
 * @Description:
 **/
@Data
public class GetSmsCaptchaRequest {
    private String phone;
}
