package com.lqf.xiaofangshu.note.biz.config;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: 李启仿
 * @date: 2025/7/13
 * @description: RocketMQ配置
 */

@Configuration
@Import(RocketMQAutoConfiguration.class)
public class RocketMQConfig {
}
