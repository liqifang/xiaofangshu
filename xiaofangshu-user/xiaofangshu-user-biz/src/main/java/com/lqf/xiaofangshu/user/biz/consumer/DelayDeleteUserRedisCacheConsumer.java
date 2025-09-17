package com.lqf.xiaofangshu.user.biz.consumer;

import com.lqf.xiaofangshu.user.biz.constant.MQConstants;
import com.lqf.xiaofangshu.user.biz.constant.RedisKeyConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author: 李启仿
 * @date: 2025/8/26
 * @description: 延时删除 Redis 笔记缓存
 */

@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(consumerGroup = "xiaohashu_group_" + MQConstants.TOPIC_DELAY_DELETE_USER_REDIS_CACHE, // Group
        topic = MQConstants.TOPIC_DELAY_DELETE_USER_REDIS_CACHE // 消费的主题 Topic
)
public class DelayDeleteUserRedisCacheConsumer implements RocketMQListener<String> {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(String body) {
        Long userId = Long.valueOf(body);
        log.info("## 延迟消息消费成功, userId: {}", userId);

        // 删除 Redis 用户缓存
        String userInfoRedisKey = RedisKeyConstants.buildUserInfoKey(userId);
        String userProfileRedisKey = RedisKeyConstants.buildUserProfileKey(userId);
        // 批量删除
        redisTemplate.delete(Arrays.asList(userInfoRedisKey, userProfileRedisKey));
    }
}