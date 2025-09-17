package com.lqf.xiaofangshu.comment.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 李启仿
 * @date: 2025/8/24
 * @description: 删除评论
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteCommentReqVO {

    @NotNull(message = "评论 ID 不能为空")
    private Long commentId;

}
