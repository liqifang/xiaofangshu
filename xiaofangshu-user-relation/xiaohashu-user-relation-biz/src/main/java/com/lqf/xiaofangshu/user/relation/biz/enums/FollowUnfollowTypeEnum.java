package com.lqf.xiaofangshu.user.relation.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: 李启仿
 * @date: 2025/7/22
 * @description: 关注、取关Type
 */

@Getter
@AllArgsConstructor
public enum FollowUnfollowTypeEnum {
    // 关注
    FOLLOW(1),
    // 取关
    UNFOLLOW(0),
    ;

    private final Integer code;

}