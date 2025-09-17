package com.lqf.xiaofangshu.user.biz.service;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.user.biz.model.vo.FindUserProfileReqVO;
import com.lqf.xiaofangshu.user.biz.model.vo.FindUserProfileRspVO;
import com.lqf.xiaofangshu.user.biz.model.vo.UpdateUserInfoReqVO;
import com.lqf.xiaofangshu.user.dto.req.*;
import com.lqf.xiaofangshu.user.dto.resp.FindUserByIdRspDTO;
import com.lqf.xiaofangshu.user.dto.resp.FindUserByPhoneRspDTO;

import java.util.List;

/**
 * @author: 李启仿
 * @date: 2025/6/24
 * @description: 用户业务
 */

public interface UserService {
    /**
     * 根据用户 ID 查询用户信息
     *
     * @param findUserByIdReqDTO
     * @return
     */
    Response<FindUserByIdRspDTO> findById(FindUserByIdReqDTO findUserByIdReqDTO);

    /**
     * 更新用户信息
     * @param updateUserInfoReqVO
     * @return
     */
    Response<?> updateUserInfo(UpdateUserInfoReqVO updateUserInfoReqVO);

    /**
     * 用户注册
     *
     * @param registerUserReqDTO
     * @return
     */
    Response<Long> register(RegisterUserReqDTO registerUserReqDTO);

    /**
     * 根据手机号查询用户信息
     *
     * @param findUserByPhoneReqDTO
     * @return
     */
    Response<FindUserByPhoneRspDTO> findByPhone(FindUserByPhoneReqDTO findUserByPhoneReqDTO);

    /**
     * 更新密码
     *
     * @param updateUserPasswordReqDTO
     * @return
     */
    Response<?> updatePassword(UpdateUserPasswordReqDTO updateUserPasswordReqDTO);

    /**
     * 批量根据用户 ID 查询用户信息
     *
     * @param findUsersByIdsReqDTO
     * @return
     */
    Response<List<FindUserByIdRspDTO>> findByIds(FindUsersByIdsReqDTO findUsersByIdsReqDTO);

    /**
     * 获取用户主页信息
     *
     * @return
     */
    Response<FindUserProfileRspVO> findUserProfile(FindUserProfileReqVO findUserProfileReqVO);
}


