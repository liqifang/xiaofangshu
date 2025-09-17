package com.lqf.xiaofangshu.user.biz.controller;

import com.lqf.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.user.biz.model.vo.FindUserProfileReqVO;
import com.lqf.xiaofangshu.user.biz.model.vo.FindUserProfileRspVO;
import com.lqf.xiaofangshu.user.biz.model.vo.UpdateUserInfoReqVO;
import com.lqf.xiaofangshu.user.biz.service.UserService;
import com.lqf.xiaofangshu.user.dto.req.*;
import com.lqf.xiaofangshu.user.dto.resp.FindUserByIdRspDTO;
import com.lqf.xiaofangshu.user.dto.resp.FindUserByPhoneRspDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 李启仿
 * @date: 2025/6/24
 * @description: 用户控制器
 */

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户信息修改
     * @param updateUserInfoReqVO
     * @return
     */
    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<?> updateUserInfo(@Validated UpdateUserInfoReqVO updateUserInfoReqVO) {
        return userService.updateUserInfo(updateUserInfoReqVO);
    }

    /**
     * 获取用户主页信息
     *
     * @return
     */
    @PostMapping(value = "/profile")
    public Response<FindUserProfileRspVO> findUserProfile(@Validated @RequestBody FindUserProfileReqVO findUserProfileReqVO) {
        return userService.findUserProfile(findUserProfileReqVO);
    }

    // ===================================== 对其他服务提供的接口 =====================================
    @PostMapping("/register")
    @ApiOperationLog(description = "用户注册")
    public Response<Long> register(@Validated @RequestBody RegisterUserReqDTO registerUserReqDTO) {
        return userService.register(registerUserReqDTO);
    }

    @PostMapping("/findByPhone")
    @ApiOperationLog(description = "手机号查询用户信息")
    public Response<FindUserByPhoneRspDTO> findByPhone(@Validated @RequestBody FindUserByPhoneReqDTO findUserByPhoneReqDTO) {
        return userService.findByPhone(findUserByPhoneReqDTO);
    }

    @PostMapping("/password/update")
    @ApiOperationLog(description = "密码更新")
    public Response<?> updatePassword(@Validated @RequestBody UpdateUserPasswordReqDTO updateUserPasswordReqDTO) {
        return userService.updatePassword(updateUserPasswordReqDTO);
    }

    @PostMapping("/findById")
    @ApiOperationLog(description = "查询用户信息")
    public Response<FindUserByIdRspDTO> findById(@Validated @RequestBody FindUserByIdReqDTO findUserByIdReqDTO) {
        return userService.findById(findUserByIdReqDTO);
    }

    @PostMapping("/findByIds")
    @ApiOperationLog(description = "批量查询用户信息")
    public Response<List<FindUserByIdRspDTO>> findByIds(@Validated @RequestBody FindUsersByIdsReqDTO findUsersByIdsReqDTO) {
        return userService.findByIds(findUsersByIdsReqDTO);
    }


}