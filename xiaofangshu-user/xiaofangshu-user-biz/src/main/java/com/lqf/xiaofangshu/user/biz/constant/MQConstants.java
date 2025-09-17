package com.lqf.xiaofangshu.user.biz.constant;

/**
 * @author: 李启仿
 * @date: 2025/8/26
 * @description: MQ 常量
 */

public interface MQConstants {

    /**
     * Topic 主题：延迟双删 Redis 用户缓存
     */
    String TOPIC_DELAY_DELETE_USER_REDIS_CACHE = "DelayDeleteUserRedisCacheTopic";
}
