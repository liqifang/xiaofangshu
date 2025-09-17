package com.lqf.xiaofangshu.auth.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 李启仿
 * @date: 2025/6/11
 * @description: 阿里云短信发送配置类
 */
@ConfigurationProperties(prefix = "aliyun")
@Component
@Data
public class AliyunAccessKeyProperties {
    private String accessKeyId;
    private String accessKeySecret;
}
