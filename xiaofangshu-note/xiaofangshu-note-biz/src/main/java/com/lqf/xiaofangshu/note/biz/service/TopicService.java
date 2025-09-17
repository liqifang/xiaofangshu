package com.lqf.xiaofangshu.note.biz.service;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.note.biz.model.vo.FindTopicListReqVO;
import com.lqf.xiaofangshu.note.biz.model.vo.FindTopicRspVO;

import java.util.List;


public interface TopicService {

    Response<List<FindTopicRspVO>> findTopicList(FindTopicListReqVO findTopicListReqVO);
}
