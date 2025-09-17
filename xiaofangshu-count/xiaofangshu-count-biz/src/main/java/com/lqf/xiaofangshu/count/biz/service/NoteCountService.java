package com.lqf.xiaofangshu.count.biz.service;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.count.dto.FindNoteCountsByIdRspDTO;
import com.lqf.xiaofangshu.count.dto.FindNoteCountsByIdsReqDTO;

import java.util.List;

/**
 * @author: 李启仿
 * @date: 2025/8/27
 * @description: 笔记计数服务
 */

public interface NoteCountService  {

    /**
     * 批量查询笔记计数
     * @param findNoteCountsByIdsReqDTO
     * @return
     */
    Response<List<FindNoteCountsByIdRspDTO>> findNotesCountData(FindNoteCountsByIdsReqDTO findNoteCountsByIdsReqDTO);
}
