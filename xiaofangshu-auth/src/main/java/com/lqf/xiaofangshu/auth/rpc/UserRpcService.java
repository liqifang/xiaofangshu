package com.lqf.xiaofangshu.auth.rpc;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.user.api.UserFeignApi;
import com.lqf.xiaofangshu.user.dto.req.FindUserByPhoneReqDTO;
import com.lqf.xiaofangshu.user.dto.req.RegisterUserReqDTO;
import com.lqf.xiaofangshu.user.dto.req.UpdateUserPasswordReqDTO;
import com.lqf.xiaofangshu.user.dto.resp.FindUserByPhoneRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: 李启仿
 * @date: 2025/6/26
 * @description: 用户服务
 */
@Component
@RequiredArgsConstructor
public class UserRpcService {

    private final UserFeignApi userFeignApi;

    /**
     * 根据手机号查询用户信息
     *
     * @param phone
     * @return
     */
    public FindUserByPhoneRspDTO findUserByPhone(String phone) {
        FindUserByPhoneReqDTO findUserByPhoneReqDTO = new FindUserByPhoneReqDTO();
        findUserByPhoneReqDTO.setPhone(phone);

        Response<FindUserByPhoneRspDTO> response = userFeignApi.findByPhone(findUserByPhoneReqDTO);

        if (!response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

    /**
     * 用户注册
     *
     * @param phone
     * @return
     */
    public Long registerUser(String phone) {
        RegisterUserReqDTO registerUserReqDTO = new RegisterUserReqDTO();
        registerUserReqDTO.setPhone(phone);

        Response<Long> response = userFeignApi.registerUser(registerUserReqDTO);

        if (!response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

    /**
     * 密码更新
     *
     * @param encodePassword
     */
    public void updatePassword(String encodePassword) {
        UpdateUserPasswordReqDTO updateUserPasswordReqDTO = new UpdateUserPasswordReqDTO();
        updateUserPasswordReqDTO.setEncodePassword(encodePassword);

        userFeignApi.updatePassword(updateUserPasswordReqDTO);
    }
}
