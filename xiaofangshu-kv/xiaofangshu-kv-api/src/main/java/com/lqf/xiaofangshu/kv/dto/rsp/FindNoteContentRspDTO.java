package com.lqf.xiaofangshu.kv.dto.rsp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author: 李启仿
 * @date: 2025/6/27
 * @description: 笔记内容
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindNoteContentRspDTO {

    /**
     * 笔记内容 UUID
     */
    private UUID uuid;

    /**
     * 笔记内容
     */
    private String content;


}
