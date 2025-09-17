package com.lqf.xiaofangshu.kv.biz.service;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.kv.dto.req.AddNoteContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.req.DeleteNoteContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.req.FindNoteContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.rsp.FindNoteContentRspDTO;

/**
 * @author: 李启仿
 * @date: 2025/6/27
 * @description: 笔记内容存储业务
 */

public interface NoteContentService {

    /**
     * 添加笔记内容
     *
     * @param addNoteContentReqDTO
     * @return
     */
    Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO);

    /**
     * 查询笔记内容
     *
     * @param findNoteContentReqDTO
     * @return
     */
    Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO);

    /**
     * 删除笔记内容
     *
     * @param deleteNoteContentReqDTO
     * @return
     */
    Response<?> deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO);

}