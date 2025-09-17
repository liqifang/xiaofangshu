package com.lqf.xiaofangshu.count.biz;

import com.lqf.framework.common.util.JsonUtils;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;

/**
 * @author: 李启仿
 * @date: 2025/8/22
 * @description: MQ 测试
 */

@SpringBootTest
public class MQTest {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 测试：模拟发送评论点赞、取消点赞消息
     */
    @Test
    void testBatchSendLikeUnlikeCommentMQ() {
        Long userId = 27L;
        Long commentId = 30002L;

        for (long i = 0; i < 32; i++) {
            // 构建消息体 DTO
            LikeUnlikeCommentMqDTO likeUnlikeCommentMqDTO = LikeUnlikeCommentMqDTO.builder()
                    .userId(userId)
                    .commentId(commentId)
                    .createTime(LocalDateTime.now())
                    .build();

            // 通过冒号连接, 可让 MQ 发送给主题 Topic 时，携带上标签 Tag
            String destination = "CommentLikeUnlikeTopic:";

            if (i % 2 == 0) { // 偶数
                likeUnlikeCommentMqDTO.setType(0); // 取消点赞
                destination = destination + "Unlike";
            } else { // 奇数
                likeUnlikeCommentMqDTO.setType(1); // 点赞
                destination = destination + "Like";
            }

            // MQ 分区键
            String hashKey = String.valueOf(userId);

            // 构建消息对象，并将 DTO 转成 Json 字符串设置到消息体中
            Message<String> message = MessageBuilder.withPayload(JsonUtils.toJsonString(likeUnlikeCommentMqDTO))
                    .build();

            // 同步发送 MQ 消息
            rocketMQTemplate.syncSendOrderly(destination, message, hashKey);
        }
    }

}
