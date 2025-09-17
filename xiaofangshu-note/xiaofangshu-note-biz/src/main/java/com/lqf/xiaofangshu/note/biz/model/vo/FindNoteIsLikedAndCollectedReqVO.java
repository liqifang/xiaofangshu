package com.lqf.xiaofangshu.note.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 李启仿
 * @date: 2025/8/25
 * @description: 获取笔记是否点赞、收藏数据
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindNoteIsLikedAndCollectedReqVO {

    @NotNull(message = "笔记 ID 不能为空")
    private Long noteId;

}