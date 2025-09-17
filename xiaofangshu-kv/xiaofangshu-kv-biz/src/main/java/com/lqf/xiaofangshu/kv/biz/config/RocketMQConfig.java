package com.lqf.xiaofangshu.kv.biz.config;

import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: 李启仿
 * @date: 2025/8/27
 * @description: MQ 配置
 */

@Configuration
@Import(RocketMQAutoConfiguration.class)
public class RocketMQConfig {
}
