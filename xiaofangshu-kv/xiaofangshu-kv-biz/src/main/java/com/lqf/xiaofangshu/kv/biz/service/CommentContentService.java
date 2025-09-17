package com.lqf.xiaofangshu.kv.biz.service;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.kv.dto.req.BatchAddCommentContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.req.BatchFindCommentContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.req.DeleteCommentContentReqDTO;

/**
 * @author: 李启仿
 * @date: 2025/8/8
 * @description: 评论内容存储业务
 */

public interface CommentContentService {


    /**
     * 批量添加评论内容
     * @param batchAddCommentContentReqDTO
     * @return
     */
    Response<?> batchAddCommentContent(BatchAddCommentContentReqDTO batchAddCommentContentReqDTO);

    /**
     * 批量查询评论内容
     * @param batchFindCommentContentReqDTO
     * @return
     */
    Response<?> batchFindCommentContent(BatchFindCommentContentReqDTO batchFindCommentContentReqDTO);

    /**
     * 删除评论内容
     * @param deleteCommentContentReqDTO
     * @return
     */
    Response<?> deleteCommentContent(DeleteCommentContentReqDTO deleteCommentContentReqDTO);


}
