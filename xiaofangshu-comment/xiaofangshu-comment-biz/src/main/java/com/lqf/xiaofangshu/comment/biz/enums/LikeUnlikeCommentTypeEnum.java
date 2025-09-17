package com.lqf.xiaofangshu.comment.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 李启仿
 * @date: 2025/8/21
 * @description: 评论点赞、取消点赞 Type
 */

@Getter
@AllArgsConstructor
public enum LikeUnlikeCommentTypeEnum {
    // 点赞
    LIKE(1),
    // 取消点赞
    UNLIKE(0),
    ;

    private final Integer code;

}