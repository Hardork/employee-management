package com.work.employee.utils;

import cn.hutool.json.JSONUtil;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.work.employee.common.ErrorCode;
import com.work.employee.exception.BusinessException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * @Author:HWQ
 * @DateTime:2023/7/2 22:35
 * @Description:
 **/
@Component
@ConfigurationProperties(prefix = "qinniu")
@Data
public class UpdateUserAvatarUrl {
    private String AK;
    private String SK;
    private String bucket;
    private String domain;

    public String uploadBase64File(String base64Data) {
        try {
            Auth auth = Auth.create(AK, SK);
            String uploadToken = auth.uploadToken(bucket);
            Configuration config = new Configuration();
            UploadManager uploadManager = new UploadManager(config);

            byte[] uploadBytes = Base64.getDecoder().decode(base64Data.split(",")[1]);
            Response response = uploadManager.put(uploadBytes, null, uploadToken);
            if (response.isOK()) {
                String fileName = JSONUtil.parseObj(response.bodyString()).getStr("key");
                return domain + "/" + fileName;
            } else {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "存储图片失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
