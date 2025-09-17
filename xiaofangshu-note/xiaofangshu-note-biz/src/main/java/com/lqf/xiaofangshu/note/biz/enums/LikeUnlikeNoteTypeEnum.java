package com.lqf.xiaofangshu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 李启仿
 * @date: 2025/7/25
 * @description: 笔记点赞、取消点赞Type
 */

@Getter
@AllArgsConstructor
public enum LikeUnlikeNoteTypeEnum {
    // 点赞
    LIKE(1),
    // 取消点赞
    UNLIKE(0),
    ;

    private final Integer code;

}
