package com.lqf.xiaofangshu.count.biz.service;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.count.dto.FindUserCountsByIdReqDTO;
import com.lqf.xiaofangshu.count.dto.FindUserCountsByIdRspDTO;

/**
 * @author: 李启仿
 * @date: 2025/8/26
 * @description: 用户服务
 */

public interface UserCountService {
    /**
     * 查询用户相关计数
     * @param findUserCountsByIdReqDTO
     * @return
     */
    Response<FindUserCountsByIdRspDTO> findUserCountData(FindUserCountsByIdReqDTO findUserCountsByIdReqDTO);
}
