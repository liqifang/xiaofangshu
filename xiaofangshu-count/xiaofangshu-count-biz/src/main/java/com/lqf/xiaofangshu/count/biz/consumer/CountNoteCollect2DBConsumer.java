package com.lqf.xiaofangshu.count.biz.consumer;

import cn.hutool.core.collection.CollUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.lqf.framework.common.util.JsonUtils;
import com.lqf.xiaofangshu.count.biz.constant.MQConstants;
import com.lqf.xiaofangshu.count.biz.domain.mapper.NoteCountDOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: 李启仿
 * @date: 2025/7/27
 * @description: 计数: 笔记收藏数落库
 */

@Component
@RocketMQMessageListener(consumerGroup = "xiaohashu_group_" + MQConstants.TOPIC_COUNT_NOTE_COLLECT_2_DB, // Group 组
        topic = MQConstants.TOPIC_COUNT_NOTE_COLLECT_2_DB // 主题 Topic
)
@Slf4j
@RequiredArgsConstructor
public class CountNoteCollect2DBConsumer implements RocketMQListener<String> {

    private final NoteCountDOMapper noteCountDOMapper;

    // 每秒创建 5000 个令牌
    private final RateLimiter rateLimiter = RateLimiter.create(5000);

    @Override
    public void onMessage(String body) {
        // 流量削峰：通过获取令牌，如果没有令牌可用，将阻塞，直到获得
        rateLimiter.acquire();

        log.info("## 消费到了 MQ 【计数: 笔记收藏数入库】, {}...", body);

        Map<Long, Integer> countMap = null;
        try {
            countMap = JsonUtils.parseMap(body, Long.class, Integer.class);
        } catch (Exception e) {
            log.error("## 解析 JSON 字符串异常", e);
        }

        if (CollUtil.isNotEmpty(countMap)) {
            // 判断数据库中 t_note_count 表，若笔记计数记录不存在，则插入；若记录已存在，则直接更新
            countMap.forEach((k, v) -> noteCountDOMapper.insertOrUpdateCollectTotalByNoteId(v, k));
        }
    }

}