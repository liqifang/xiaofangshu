package com.lqf.xiaofangshu.note.biz.model.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 李启仿
 * @date: 2025/7/27
 * @description: 取消收藏笔记
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnCollectNoteReqVO {

    @NotNull(message = "笔记 ID 不能为空")
    private Long id;

}