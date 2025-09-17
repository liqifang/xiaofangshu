package com.lqf.xiaofangshu.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.lqf.framework.common.exception.BizException;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.auth.constant.RedisKeyConstants;
import com.lqf.xiaofangshu.auth.enums.ResponseCodeEnum;
import com.lqf.xiaofangshu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.lqf.xiaofangshu.auth.service.VerificationCodeService;
import com.lqf.xiaofangshu.auth.sms.AliyunSmsHelper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: 李启仿
 * @date: 2025/6/10
 * @description:
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final RedisTemplate<String, Object> redisTemplate;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final AliyunSmsHelper aliyunSmsHelper;

    @Override
    public Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO) {
        String phone = sendVerificationCodeReqVO.getPhone();

        // 构造验证码
        String key = RedisKeyConstants.buildVerificationCodeKey(phone);

        // 判断redis中是否已经存在验证码
        Boolean isSend = redisTemplate.hasKey(key);
        if (Boolean.TRUE.equals(isSend)) {
            // 存在则抛出异常
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }

        // 不存在，构建验证码
        String verifyCode = RandomUtil.randomNumbers(6);

        // 调用第三方短信发送服务
        threadPoolTaskExecutor.submit(() -> {
            String signName = "阿里云短信测试";
            String templateCode = "SMS_154950909";
            String templateParam = String.format("{\"code\":\"%s\"}", verifyCode);
            aliyunSmsHelper.sendMessage(signName, templateCode, phone, templateParam);
        });

        log.info("==> 手机号：{}，已发送验证码：{}", phone, verifyCode);

        // 将验证码信息存入redis
        redisTemplate.opsForValue().set(key, verifyCode, 5, TimeUnit.MINUTES);

        return Response.success();
    }
}
