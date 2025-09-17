package com.lqf.xiaofangshu.note.biz.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class NoteLikeDO {
    private Long id;

    private Long userId;

    private Long noteId;

    private LocalDateTime createTime;

    private Integer status;
}