package com.lqf.xiaofangshu.note.biz.service;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.note.biz.model.vo.FindChannelRspVO;

import java.util.List;


public interface ChannelService {

    /**
     * 查询所有频道
     * @return
     */
    Response<List<FindChannelRspVO>> findChannelList();
}
