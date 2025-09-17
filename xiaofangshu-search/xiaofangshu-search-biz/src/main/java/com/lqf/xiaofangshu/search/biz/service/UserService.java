package com.lqf.xiaofangshu.search.biz.service;

import com.lqf.framework.common.response.PageResponse;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.search.dto.RebuildUserDocumentReqDTO;
import com.lqf.xiaofangshu.search.biz.model.vo.SearchUserReqVO;
import com.lqf.xiaofangshu.search.biz.model.vo.SearchUserRspVO;

/**
 * @author: 李启仿
 * @date: 2025/8/3
 * @description: 用户搜索业务层
 */

public interface UserService {

    /**
     * 搜索用户
     * @param searchUserReqVO
     * @return
     */
    PageResponse<SearchUserRspVO> searchUser(SearchUserReqVO searchUserReqVO);

    /**
     * 重建用户文档
     * @param rebuildUserDocumentReqDTO
     * @return
     */
    Response<Long> rebuildDocument(RebuildUserDocumentReqDTO rebuildUserDocumentReqDTO);
}