package com.lqf.xiaofangshu.user.relation.biz.service;

import com.lqf.framework.common.response.PageResponse;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.user.relation.biz.model.vo.*;

/**
 * @author: 李启仿
 * @date: 2025/7/14
 * @description: 关注业务
 */

public interface RelationService {

    /**
     * 关注用户
     * @param followUserReqVO
     * @return
     */
    Response<?> follow(FollowUserReqVO followUserReqVO);

    /**
     * 取关用户
     * @param unfollowUserReqVO
     * @return
     */
    Response<?> unfollow(UnfollowUserReqVO unfollowUserReqVO);

    /**
     * 查询关注列表
     * @param findFollowingListReqVO
     * @return
     */
    PageResponse<FindFollowingUserRspVO> findFollowingList(FindFollowingListReqVO findFollowingListReqVO);

    /**
     * 查询粉丝列表
     * @param findFansListReqVO
     * @return
     */
    PageResponse<FindFansUserRspVO> findFansList(FindFansListReqVO findFansListReqVO);
}
