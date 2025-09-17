package com.lqf.xiaofangshu.search.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 李启仿
 * @date: 2025/9/3
 * @description: 重建用户文档
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RebuildUserDocumentReqDTO {

    @NotNull(message = "用户 ID 不能为空")
    private Long id;

}
