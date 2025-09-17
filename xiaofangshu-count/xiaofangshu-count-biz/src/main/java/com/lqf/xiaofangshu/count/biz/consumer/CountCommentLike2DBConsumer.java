package com.lqf.xiaofangshu.count.biz.consumer;

import cn.hutool.core.collection.CollUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.lqf.framework.common.util.JsonUtils;
import com.lqf.xiaofangshu.count.biz.constant.MQConstants;
import com.lqf.xiaofangshu.count.biz.domain.mapper.CommentDOMapper;
import com.lqf.xiaofangshu.count.biz.model.dto.AggregationCountLikeUnlikeCommentMqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: 李启仿
 * @date: 2025/8/24
 * @description: 计数: 评论点赞数落库
 */

@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(consumerGroup = "xiaohashu_group_" + MQConstants.TOPIC_COUNT_COMMENT_LIKE_2_DB, // Group 组
        topic = MQConstants.TOPIC_COUNT_COMMENT_LIKE_2_DB // 主题 Topic
)
public class CountCommentLike2DBConsumer implements RocketMQListener<String> {

    private final CommentDOMapper commentDOMapper;

    // 每秒创建 5000 个令牌
    private final RateLimiter rateLimiter = RateLimiter.create(5000);

    @Override
    public void onMessage(String body) {
        // 流量削峰：通过获取令牌，如果没有令牌可用，将阻塞，直到获得
        rateLimiter.acquire();

        log.info("## 消费到了 MQ 【计数: 评论点赞数入库】, {}...", body);

        List<AggregationCountLikeUnlikeCommentMqDTO> countList = null;
        try {
            countList = JsonUtils.parseList(body, AggregationCountLikeUnlikeCommentMqDTO.class);
        } catch (Exception e) {
            log.error("## 解析 JSON 字符串异常", e);
        }

        if (CollUtil.isNotEmpty(countList)) {
            // 更新评论点赞数
            countList.forEach(item -> {
                Long commentId = item.getCommentId();
                Integer count = item.getCount();

                commentDOMapper.updateLikeTotalByCommentId(count, commentId);
            });
        }
    }

}