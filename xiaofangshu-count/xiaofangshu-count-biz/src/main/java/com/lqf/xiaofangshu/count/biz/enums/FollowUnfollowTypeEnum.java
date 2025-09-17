package com.lqf.xiaofangshu.count.biz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author: 李启仿
 * @date: 2025/7/23
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

    public static FollowUnfollowTypeEnum valueOf(Integer code) {
        for (FollowUnfollowTypeEnum followUnfollowTypeEnum : FollowUnfollowTypeEnum.values()) {
            if (Objects.equals(code, followUnfollowTypeEnum.getCode())) {
                return followUnfollowTypeEnum;
            }
        }
        return null;
    }

}