package com.lqf.xiaofangshu.note.biz.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: 李启仿
 * @date: 2025/7/11
 * @description: 笔记发布
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublishNoteReqVO {

    @NotNull(message = "笔记类型不能为空")
    private Integer type;

    private List<String> imgUris;

    private String videoUri;

    @NotBlank(message = "笔记标题不能为空")
    private String title;

    private String content;

    // private Long topicId; TODO 移除

    /**
     * 支持用户添加多话题
     */
    private List<Object> topics;

    /**
     * 目前平台不支持人工智能对话题归类到不同频道下，故牺牲一点用户体验，让用户手动选择频道
     */
    @NotNull(message = "频道不能为空")
    private Long channelId;

}
