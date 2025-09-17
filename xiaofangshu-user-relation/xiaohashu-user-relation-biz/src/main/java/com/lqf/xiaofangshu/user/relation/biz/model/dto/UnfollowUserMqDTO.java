package com.lqf.xiaofangshu.user.relation.biz.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: 李启仿
 * @date: 2025/7/16
 * @description: 取关
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnfollowUserMqDTO {

    private Long userId;

    private Long unfollowUserId;

    private LocalDateTime createTime;
}

