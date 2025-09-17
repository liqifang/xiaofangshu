package com.lqf.xiaofangshu.search.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 李启仿
 * @date: 2025/9/2
 * @description: 笔记可见性
 */

@Getter
@AllArgsConstructor
public enum NoteVisibleEnum {

    PUBLIC(0), // 公开，所有人可见
    PRIVATE(1); // 仅自己可见

    private final Integer code;

}