package com.lqf.xiaofangshu.user.biz.config;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: 李启仿
 * @date: 2025/8/26
 * @description:
 */

@Configuration
@Import(RocketMQAutoConfiguration.class)
public class RocketMQConfig {
}
