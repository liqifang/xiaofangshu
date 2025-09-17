package com.lqf.xiaofangshu.count.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 李启仿
 * @date: 2025/8/12
 * @description: 发送给计数服务：评论发布
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountPublishCommentMqDTO {

    /**
     * 笔记 ID
     */
    private Long noteId;

    /**
     * 评论 ID
     */
    private Long commentId;

    /**
     * 评论级别
     */
    private Integer level;

    /**
     * 父 ID
     */
    private Long parentId;

}
