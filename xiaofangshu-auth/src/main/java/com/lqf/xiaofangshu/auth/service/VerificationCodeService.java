package com.lqf.xiaofangshu.auth.service;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;

/**
 * @author: 李启仿
 * @date: 2025/6/10
 * @description: 验证
 */

public interface VerificationCodeService {

    /**
     * 发送短信验证码
     *
     * @param sendVerificationCodeReqVO
     * @return
     */
    Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO);
}
