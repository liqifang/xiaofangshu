package com.lqf.xiaofangshu.note.biz.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 李启仿
 * @date: 2025/8/25
 * @description: 获取笔记是否点赞、收藏数据
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindNoteIsLikedAndCollectedRspVO {

    /**
     * 笔记 ID
     */
    private Long noteId;

    /**
     * 是否被当前登录的用户点赞
     */
    private Boolean isLiked;

    /**
     * 是否被当前登录的用户收藏
     */
    private Boolean isCollected;

}
