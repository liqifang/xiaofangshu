package com.lqf.xiaofangshu.count.biz.consumer;

import cn.hutool.core.collection.CollUtil;
import com.github.phantomthief.collection.BufferTrigger;
import com.google.common.collect.Lists;
import com.lqf.framework.common.util.JsonUtils;
import com.lqf.xiaofangshu.count.biz.constant.MQConstants;
import com.lqf.xiaofangshu.count.biz.constant.RedisKeyConstants;
import com.lqf.xiaofangshu.count.biz.domain.mapper.NoteCountDOMapper;
import com.lqf.xiaofangshu.count.biz.model.dto.CountPublishCommentMqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 李启仿
 * @date: 2025/8/12
 * @description: 计数: 笔记评论数
 */

@Component
@RocketMQMessageListener(consumerGroup = "xiaohashu_group_" + MQConstants.TOPIC_COUNT_NOTE_COMMENT, // Group 组
        topic = MQConstants.TOPIC_COUNT_NOTE_COMMENT // 主题 Topic
)
@Slf4j
@RequiredArgsConstructor
public class CountNoteCommentConsumer implements RocketMQListener<String> {

    private final NoteCountDOMapper noteCountDOMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    private final BufferTrigger<String> bufferTrigger = BufferTrigger.<String>batchBlocking()
            .bufferSize(50000) // 缓存队列的最大容量
            .batchSize(1000)   // 一批次最多聚合 1000 条
            .linger(Duration.ofSeconds(1)) // 多久聚合一次（1s 一次）
            .setConsumerEx(this::consumeMessage) // 设置消费者方法
            .build();

    @Override
    public void onMessage(String body) {
        // 往 bufferTrigger 中添加元素
        bufferTrigger.enqueue(body);
    }

    private void consumeMessage(List<String> bodys) {
        log.info("==> 【笔记评论数】聚合消息, size: {}", bodys.size());
        log.info("==> 【笔记评论数】聚合消息, {}", JsonUtils.toJsonString(bodys));

        // TODO:
        // 将聚合后的消息体 Json 转 List<CountPublishCommentMqDTO> 集合；
        List<CountPublishCommentMqDTO> countPublishCommentMqDTOList = Lists.newArrayList();
        bodys.forEach(body -> {
            try {
                List<CountPublishCommentMqDTO> list = JsonUtils.parseList(body, CountPublishCommentMqDTO.class);
                countPublishCommentMqDTOList.addAll(list);
            } catch (Exception e) {
                log.error("", e);
            }
        });

        // 按笔记 ID 进行分组，因为这一批评论消息，可能归属于不同笔记；
        Map<Long, List<CountPublishCommentMqDTO>> groupMap = countPublishCommentMqDTOList.stream()
                .collect(Collectors.groupingBy(CountPublishCommentMqDTO::getNoteId));

        // 循环分组 Map , 拿到对应的笔记 ID 与评论数；
        for (Map.Entry<Long, List<CountPublishCommentMqDTO>> entry : groupMap.entrySet()) {
            // 笔记 ID
            Long noteId = entry.getKey();
            // 评论数
            int count = CollUtil.size(entry.getValue());

            // 更新 Redis 缓存中的笔记评论总数
            // 构建 Key
            String noteCountHashKey = RedisKeyConstants.buildCountNoteKey(noteId);
            // 判断 Hash 是否存在
            boolean hasKey = Boolean.TRUE.equals(redisTemplate.hasKey(noteCountHashKey));

            // 若 Hash 存在
            if (hasKey) {
                // 累加更新
                redisTemplate.opsForHash()
                        .increment(noteCountHashKey, RedisKeyConstants.FIELD_COMMENT_TOTAL, count);
            }

            // 若评论数大于零，则执行更新操作：累加评论总数
            if (count > 0) {
                noteCountDOMapper.insertOrUpdateCommentTotalByNoteId(count, noteId);
            }
        }
    }
}

