package com.lqf.xiaofangshu.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.lqf.framework.biz.context.holder.LoginUserContextHolder;
import com.lqf.framework.common.exception.BizException;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.auth.constant.RedisKeyConstants;
import com.lqf.xiaofangshu.auth.enums.LoginTypeEnum;
import com.lqf.xiaofangshu.auth.enums.ResponseCodeEnum;
import com.lqf.xiaofangshu.auth.model.vo.user.UpdatePasswordReqVO;
import com.lqf.xiaofangshu.auth.model.vo.user.UserLoginReqVO;
import com.lqf.xiaofangshu.auth.rpc.UserRpcService;
import com.lqf.xiaofangshu.auth.service.AuthService;
import com.lqf.xiaofangshu.user.dto.resp.FindUserByPhoneRspDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @author: 李启仿
 * @date: 2025/6/11
 * @description: 用户服务实现类
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final UserRpcService userRpcService;

    @Override
    public Response<?> updatePassword(UpdatePasswordReqVO updatePasswordReqVO) {
        // 获取用户名密码
        String newPassword = updatePasswordReqVO.getNewPassword();
        // 密码加密
        String encodePassword = passwordEncoder.encode(newPassword);

        // RPC: 调用用户服务：更新密码
        userRpcService.updatePassword(encodePassword);

        return Response.success();
    }

    @Override
    public Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO) {
        // 获取用户信息
        Integer type = userLoginReqVO.getType();
        String phone = userLoginReqVO.getPhone();
        // 获取登录类型
        LoginTypeEnum loginTypeEnum = LoginTypeEnum.valueOf(type);

        Long userId = null;

        // 判断登录类型
        if (loginTypeEnum == LoginTypeEnum.VERIFICATION_CODE) {
            // 验证码登录
            // 获取验证码
            String code = userLoginReqVO.getCode();
            // 验证码为空
            Preconditions.checkArgument(StringUtils.isNotBlank(code), "验证码不能为空");

            // 2.构建验证码
            String RedisKey = RedisKeyConstants.buildVerificationCodeKey(phone);
            // 判断验证码是否存入Redis
            String sentCode = (String) redisTemplate.opsForValue().get(RedisKey);

            // 若验证码错误
            if (!StringUtils.equals(code, sentCode)) {
                throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
            }

            // RPC: 调用用户服务，注册用户
            Long userIdTmp = userRpcService.registerUser(phone);
            log.info("注册的userId: {}", userIdTmp);

            // 若调用用户服务，返回的用户 ID 为空，则提示登录失败
            if (Objects.isNull(userIdTmp)) {
                throw new BizException(ResponseCodeEnum.LOGIN_FAIL);
            }

            userId = userIdTmp;
            // TODO： 测试
            // LoginUserContextHolder.setUserId(userId);

        } else {
            // 密码登录
            String password = userLoginReqVO.getPassword();

            // RPC: 调用用户服务，通过手机号查询用户
            FindUserByPhoneRspDTO findUserByPhoneRspDTO = userRpcService.findUserByPhone(phone);

            // 判断该手机号是否注册
            if (Objects.isNull(findUserByPhoneRspDTO)) {
                throw new BizException(ResponseCodeEnum.USER_NOT_FOUND);
            }

            // 拿到密文密码
            String encodePassword = findUserByPhoneRspDTO.getPassword();

            // 匹配密码是否一致
            boolean isPasswordCorrect = passwordEncoder.matches(password, encodePassword);

            // 如果不正确，则抛出业务异常，提示用户名或者密码不正确
            if (!isPasswordCorrect) {
                throw new BizException(ResponseCodeEnum.PHONE_OR_PASSWORD_ERROR);
            }

            userId = findUserByPhoneRspDTO.getId();
        }

        // 已登录用户，颁发token令牌
        StpUtil.login(userId);

        // 获取 Token 令牌
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        // 删除reids中的验证码
        redisTemplate.delete(RedisKeyConstants.buildVerificationCodeKey(phone));

        // 返回 Token 令牌
        return Response.success(tokenInfo.tokenValue);
    }

    @Override
    public Response<?> logout() {
        Long userId = LoginUserContextHolder.getUserId();

        log.info("==> 用户退出登录, userId: {}", userId);

        // 退出登录 (指定用户 ID)
        StpUtil.logout(userId);

        return Response.success();
    }

}
