package com.lqf.xiaofangshu.comment.biz.rpc;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.lqf.framework.common.enums.DateConstantsEnum;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.comment.biz.model.bo.CommentBO;
import com.lqf.xiaofangshu.kv.api.KeyValueFeignApi;
import com.lqf.xiaofangshu.kv.dto.req.*;
import com.lqf.xiaofangshu.kv.dto.rsp.FindCommentContentRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author: 李启仿
 * @date: 2025/8/9
 * @description: KV 键值服务
 */

@Component
@RequiredArgsConstructor
public class KeyValueRpcService {

    private final KeyValueFeignApi keyValueFeignApi;

    /**
     * 批量存储评论内容
     * @param commentBOS
     * @return
     */
    public boolean batchSaveCommentContent(List<CommentBO> commentBOS) {
        List<CommentContentReqDTO> comments = Lists.newArrayList();

        // BO 转 DTO
        commentBOS.forEach(commentBO -> {
            CommentContentReqDTO commentContentReqDTO = CommentContentReqDTO.builder()
                    .noteId(commentBO.getNoteId())
                    .content(commentBO.getContent())
                    .contentId(commentBO.getContentUuid())
                    .yearMonth(commentBO.getCreateTime().format(DateConstantsEnum.DATE_FORMAT_Y_M.getValue()))
                    .build();
            comments.add(commentContentReqDTO);
        });

        // 构建接口入参实体类
        BatchAddCommentContentReqDTO batchAddCommentContentReqDTO = BatchAddCommentContentReqDTO.builder()
                .comments(comments)
                .build();

        // 调用 KV 存储服务
        Response<?> response = keyValueFeignApi.batchAddCommentContent(batchAddCommentContentReqDTO);

        // 若返参中 success 为 false, 则主动抛出异常，以便调用层回滚事务
        if (!response.isSuccess()) {
            throw new RuntimeException("批量保存评论内容失败");
        }

        return true;
    }

    /**
     * 批量查询评论内容
     * @param noteId
     * @param findCommentContentReqDTOS
     * @return
     */
    public List<FindCommentContentRspDTO> batchFindCommentContent(Long noteId, List<FindCommentContentReqDTO> findCommentContentReqDTOS) {
        BatchFindCommentContentReqDTO bathFindCommentContentReqDTO = BatchFindCommentContentReqDTO.builder()
                .noteId(noteId)
                .commentContentKeys(findCommentContentReqDTOS)
                .build();

        Response<List<FindCommentContentRspDTO>> response = keyValueFeignApi.batchFindCommentContent(bathFindCommentContentReqDTO);

        if (!response.isSuccess() || Objects.isNull(response.getData()) || CollUtil.isEmpty(response.getData())) {
            return null;
        }

        return response.getData();
    }

    /**
     * 删除评论内容
     * @param noteId
     * @param createTime
     * @param contentId
     * @return
     */
    public boolean deleteCommentContent(Long noteId, LocalDateTime createTime, String contentId) {
        DeleteCommentContentReqDTO deleteCommentContentReqDTO = DeleteCommentContentReqDTO.builder()
                .noteId(noteId)
                .yearMonth(DateConstantsEnum.DATE_FORMAT_Y_M.getValue().format(createTime))
                .contentId(contentId)
                .build();

        // 调用 KV 存储服务
        Response<?> response = keyValueFeignApi.deleteCommentContent(deleteCommentContentReqDTO);

        if (!response.isSuccess()) {
            throw new RuntimeException("删除评论内容失败");
        }

        return true;
    }


}