package com.lqf.xiaofangshu.search.biz.service;

import com.lqf.framework.common.response.PageResponse;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.search.dto.RebuildNoteDocumentReqDTO;
import com.lqf.xiaofangshu.search.biz.model.vo.SearchNoteReqVO;
import com.lqf.xiaofangshu.search.biz.model.vo.SearchNoteRspVO;

/**
 * @author: 李启仿
 * @date: 2025/8/3
 * @description:
 */

public interface NoteService {

    /**
     * 搜索笔记
     * @param searchNoteReqVO
     * @return
     */
    PageResponse<SearchNoteRspVO> searchNote(SearchNoteReqVO searchNoteReqVO);

    /**
     * 重建笔记文档
     * @param rebuildNoteDocumentReqDTO
     * @return
     */
    Response<Long> rebuildDocument(RebuildNoteDocumentReqDTO rebuildNoteDocumentReqDTO);
}