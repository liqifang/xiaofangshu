package com.lqf.xiaofangshu.data.align.constant;

/**
 * @author: 李启仿
 * @date: 2025/7/29
 * @description: MQ常量
 */

public interface MQConstants {

    /**
     * Topic: 计数 - 笔记点赞数
     */
    String TOPIC_COUNT_NOTE_LIKE = "CountNoteLikeTopic";

    /**
     * Topic: 计数 - 笔记收藏数
     */
    String TOPIC_COUNT_NOTE_COLLECT = "CountNoteCollectTopic";

    /**
     * Topic: 笔记操作（发布、删除）
     */
    String TOPIC_NOTE_OPERATE = "NoteOperateTopic";

    /**
     * Topic: 关注数计数
     */
    String TOPIC_COUNT_FOLLOWING = "CountFollowingTopic";


}
