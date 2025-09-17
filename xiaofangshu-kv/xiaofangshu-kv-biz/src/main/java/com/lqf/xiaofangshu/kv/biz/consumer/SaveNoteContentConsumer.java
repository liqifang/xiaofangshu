package com.lqf.xiaofangshu.kv.biz.consumer;

import com.lqf.framework.common.util.JsonUtils;
import com.lqf.xiaofangshu.kv.biz.constant.MQConstants;
import com.lqf.xiaofangshu.kv.biz.model.dto.PublishNoteDTO;
import com.lqf.xiaofangshu.kv.biz.service.NoteContentService;
import com.lqf.xiaofangshu.kv.dto.req.AddNoteContentReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author: 李启仿
 * @date: 2025/8/27
 * @description: 事务消息：保存笔记正文
 */

@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(consumerGroup = "xiaohashu_group_" + MQConstants.TOPIC_PUBLISH_NOTE_TRANSACTION, // Group 组
        topic = MQConstants.TOPIC_PUBLISH_NOTE_TRANSACTION // 消费的主题 Topic
)
public class SaveNoteContentConsumer implements RocketMQListener<Message> {

    private final NoteContentService noteContentService;

    @Override
    public void onMessage(Message message) {
        // 消息体
        String bodyJsonStr = new String(message.getBody());

        log.info("## SaveNoteContentConsumer 消费了事务消息 {}", bodyJsonStr);

        // 笔记正文保存到 Cassandra 中
        if (StringUtils.isNotBlank(bodyJsonStr)) {
            PublishNoteDTO publishNoteDTO = JsonUtils.parseObject(bodyJsonStr, PublishNoteDTO.class);
            String content = publishNoteDTO.getContent();
            String uuid = publishNoteDTO.getContentUuid();

            noteContentService.addNoteContent(AddNoteContentReqDTO.builder()
                    .uuid(uuid)
                    .content(content)
                    .build());
        }
    }

}