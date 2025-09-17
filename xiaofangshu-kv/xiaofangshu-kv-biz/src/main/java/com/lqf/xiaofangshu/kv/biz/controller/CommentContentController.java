package com.lqf.xiaofangshu.kv.biz.controller;

import com.lqf.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.kv.biz.service.CommentContentService;
import com.lqf.xiaofangshu.kv.dto.req.BatchAddCommentContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.req.BatchFindCommentContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.req.DeleteCommentContentReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 李启仿
 * @date: 2025/8/8
 * @description: 评论内容
 */

@RestController
@RequestMapping("/kv")
@Slf4j
@RequiredArgsConstructor
public class CommentContentController {

    private final CommentContentService commentContentService;

    @PostMapping(value = "/comment/content/batchAdd")
    @ApiOperationLog(description = "批量存储评论内容")
    public Response<?> batchAddCommentContent(@Validated @RequestBody BatchAddCommentContentReqDTO batchAddCommentContentReqDTO) {
        return commentContentService.batchAddCommentContent(batchAddCommentContentReqDTO);
    }

    @PostMapping(value = "/comment/content/batchFind")
    @ApiOperationLog(description = "批量查询评论内容")
    public Response<?> batchFindCommentContent(@Validated @RequestBody BatchFindCommentContentReqDTO batchFindCommentContentReqDTO) {
        return commentContentService.batchFindCommentContent(batchFindCommentContentReqDTO);
    }

    @PostMapping(value = "/comment/content/delete")
    @ApiOperationLog(description = "删除评论内容")
    public Response<?> deleteCommentContent(@Validated @RequestBody DeleteCommentContentReqDTO deleteCommentContentReqDTO) {
        return commentContentService.deleteCommentContent(deleteCommentContentReqDTO);
    }
}