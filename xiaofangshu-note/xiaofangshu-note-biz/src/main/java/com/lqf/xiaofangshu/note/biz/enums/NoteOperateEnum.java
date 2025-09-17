package com.lqf.xiaofangshu.note.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 李启仿
 * @date: 2025/7/27
 * @description: 笔记操作
 */

@Getter
@AllArgsConstructor
public enum NoteOperateEnum {
    // 笔记发布
    PUBLISH(1),
    // 笔记删除
    DELETE(0),
    ;

    private final Integer code;

}
