package com.lqf.xiaofangshu.user.biz.rpc;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.oss.api.FileFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 李启仿
 * @date: 2025/6/24
 * @description: 对象存储服务调用
 */

@Component
public class OssRpcService {

    @Resource
    private FileFeignApi fileFeignApi;

    public String uploadFile(MultipartFile file) {
        // 调用对象存储服务上传文件
        Response<?> response = fileFeignApi.uploadFile(file);

        if (!response.isSuccess()) {
            return null;
        }

        // 返回图片访问链接
        return (String) response.getData();
    }
}