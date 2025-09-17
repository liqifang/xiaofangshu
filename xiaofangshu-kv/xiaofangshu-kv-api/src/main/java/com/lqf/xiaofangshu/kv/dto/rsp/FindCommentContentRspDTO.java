package com.lqf.xiaofangshu.kv.dto.rsp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 李启仿
 * @date: 2025/8/20
 * @description: 评论内容
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCommentContentRspDTO {

    /**
     * 评论内容 UUID
     */
    private String contentId;

    /**
     * 评论内容
     */
    private String content;

}