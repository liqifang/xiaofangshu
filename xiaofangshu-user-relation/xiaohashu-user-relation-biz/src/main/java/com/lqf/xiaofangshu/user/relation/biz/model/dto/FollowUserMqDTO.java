package com.lqf.xiaofangshu.user.relation.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: 李启仿
 * @date: 2025/7/15
 * @description: 关注用户
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowUserMqDTO {

    private Long userId;

    private Long followUserId;

    private LocalDateTime createTime;
}
