package com.lqf.xiaofangshu.oss.biz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 李启仿
 * @date: 2025/6/23
 * @description: Minio配置
 */

@ConfigurationProperties(prefix = "storage.minio")
@Component
@Data
public class MinioProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}