package com.lqf.xiaofangshu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 李启仿
 * @date: 2025/7/27
 * @description: 笔记收藏、取消收藏 Type
 */

@Getter
@AllArgsConstructor
public enum CollectUnCollectNoteTypeEnum {
    // 收藏
    COLLECT(1),
    // 取消收藏
    UN_COLLECT(0),
    ;

    private final Integer code;

}
