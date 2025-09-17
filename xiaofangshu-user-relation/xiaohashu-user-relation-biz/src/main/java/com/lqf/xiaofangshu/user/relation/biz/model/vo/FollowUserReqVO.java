package com.lqf.xiaofangshu.user.relation.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 李启仿
 * @date: 2025/7/14
 * @description: 关注用户
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowUserReqVO {

    @NotNull(message = "被关注用户 ID 不能为空")
    private Long followUserId;
}