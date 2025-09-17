package com.lqf.xiaofangshu.kv.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.kv.biz.domain.dataobject.CommentContentDO;
import com.lqf.xiaofangshu.kv.biz.domain.dataobject.CommentContentPrimaryKey;
import com.lqf.xiaofangshu.kv.biz.domain.reository.CommentContentRepository;
import com.lqf.xiaofangshu.kv.biz.service.CommentContentService;
import com.lqf.xiaofangshu.kv.dto.req.*;
import com.lqf.xiaofangshu.kv.dto.rsp.FindCommentContentRspDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author: 李启仿
 * @date: 2025/8/8
 * @description: 评论内容业务
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentContentServiceImpl implements CommentContentService {

    private final CassandraTemplate cassandraTemplate;
    private final CommentContentRepository commentContentRepository;


    /**
     * 批量添加评论内容
     *
     * @param batchAddCommentContentReqDTO
     * @return
     */
    @Override
    public Response<?> batchAddCommentContent(BatchAddCommentContentReqDTO batchAddCommentContentReqDTO) {
        List<CommentContentReqDTO> comments = batchAddCommentContentReqDTO.getComments();

        // DTO 转 DO
        List<CommentContentDO> contentDOS = comments.stream()
                .map(commentContentReqDTO -> {
                    // 构建主键类
                    CommentContentPrimaryKey commentContentPrimaryKey = CommentContentPrimaryKey.builder()
                            .noteId(commentContentReqDTO.getNoteId())
                            .yearMonth(commentContentReqDTO.getYearMonth())
                            .contentId(UUID.fromString(commentContentReqDTO.getContentId()))
                            .build();

                    // DO 实体类
                    return CommentContentDO.builder()
                            .primaryKey(commentContentPrimaryKey)
                            .content(commentContentReqDTO.getContent())
                            .build();
                }).toList();

        // 批量插入
        cassandraTemplate.batchOps()
                .insert(contentDOS)
                .execute();

        return Response.success();
    }

    /**
     * 批量查询评论内容
     *
     * @param batchFindCommentContentReqDTO
     * @return
     */
    @Override
    public Response<?> batchFindCommentContent(BatchFindCommentContentReqDTO batchFindCommentContentReqDTO) {
        // 归属的笔记 ID
        Long noteId = batchFindCommentContentReqDTO.getNoteId();

        // 评论内容
        List<FindCommentContentReqDTO> commentContentKeys = batchFindCommentContentReqDTO.getCommentContentKeys();

        // 过滤出年月
        List<String> yearMonths = commentContentKeys.stream()
                .map(FindCommentContentReqDTO::getYearMonth)
                .distinct() // 去重
                .collect(Collectors.toList());

        // 过滤出评论内容 UUID
        List<UUID> contentIds = commentContentKeys.stream()
                .map(commentContentKey -> UUID.fromString(commentContentKey.getContentId()))
                .distinct() // 去重
                .collect(Collectors.toList());

        // 批量查询 Cassandra
        List<CommentContentDO> commentContentDOS = commentContentRepository
                .findByPrimaryKeyNoteIdAndPrimaryKeyYearMonthInAndPrimaryKeyContentIdIn(noteId, yearMonths, contentIds);

        // DO 转 DTO
        List<FindCommentContentRspDTO> findCommentContentRspDTOS = Lists.newArrayList();
        if (CollUtil.isNotEmpty(commentContentDOS)) {
            findCommentContentRspDTOS = commentContentDOS.stream()
                    .map(commentContentDO -> FindCommentContentRspDTO.builder()
                            .contentId(String.valueOf(commentContentDO.getPrimaryKey().getContentId()))
                            .content(commentContentDO.getContent())
                            .build())
                    .toList();
        }

        return Response.success(findCommentContentRspDTOS);
    }

    /**
     * 删除评论内容
     *
     * @param deleteCommentContentReqDTO
     * @return
     */
    @Override
    public Response<?> deleteCommentContent(DeleteCommentContentReqDTO deleteCommentContentReqDTO) {
        Long noteId = deleteCommentContentReqDTO.getNoteId();
        String yearMonth = deleteCommentContentReqDTO.getYearMonth();
        String contentId = deleteCommentContentReqDTO.getContentId();

        // 删除评论正文
        commentContentRepository.deleteByPrimaryKeyNoteIdAndPrimaryKeyYearMonthAndPrimaryKeyContentId(
                noteId, yearMonth, UUID.fromString(contentId));

        return Response.success();
    }
}
