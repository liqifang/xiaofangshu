package com.lqf.xiaofangshu.note.biz.service;


import com.lqf.framework.common.response.PageResponse;
import com.lqf.xiaofangshu.note.biz.model.vo.FindProfileNotePageListReqVO;
import com.lqf.xiaofangshu.note.biz.model.vo.FindProfileNoteRspVO;


public interface ProfileService {

    PageResponse<FindProfileNoteRspVO> findNoteList(FindProfileNotePageListReqVO findProfileNotePageListReqVO);
}
