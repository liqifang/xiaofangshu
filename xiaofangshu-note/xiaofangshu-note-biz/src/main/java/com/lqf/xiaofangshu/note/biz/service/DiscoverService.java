package com.lqf.xiaofangshu.note.biz.service;


import com.lqf.framework.common.response.PageResponse;
import com.lqf.xiaofangshu.note.biz.model.vo.FindDiscoverNotePageListReqVO;
import com.lqf.xiaofangshu.note.biz.model.vo.FindDiscoverNoteRspVO;

/**
 * @author: 李启仿
 * @date: 2025/8/7 15:41
 * @description: 发现页业务
 **/
public interface DiscoverService {

    PageResponse<FindDiscoverNoteRspVO> findNoteList(FindDiscoverNotePageListReqVO findDiscoverNoteListReqVO);
}
