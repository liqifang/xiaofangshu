package com.lqf.xiaofangshu.oss.biz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 李启仿
 * @date: 2025/6/24
 * @description: 阿里云OSS配置
 */

@ConfigurationProperties(prefix = "storage.aliyun-oss")
@Component
@Data
public class AliyunOSSProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}