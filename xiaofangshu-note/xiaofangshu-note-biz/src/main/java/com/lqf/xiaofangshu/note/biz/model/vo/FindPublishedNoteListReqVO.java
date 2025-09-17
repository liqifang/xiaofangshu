package com.lqf.xiaofangshu.note.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 李启仿
 * @date: 2025/8/27
 * @description: 个人主页 - 已发布笔记列表
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPublishedNoteListReqVO {

    @NotNull(message = "用户 ID 不能为空")
    private Long userId;

    /**
     * 游标，即笔记 ID，用于分页使用
     */
    private Long cursor;

}