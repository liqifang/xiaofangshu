package com.lqf.framework.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: 李启仿
 * @date: 2025/6/8
 * @description: 业务异常类
 */

@Getter
@Setter
// Biz 是 Business 英文的缩写，代表业务的意思。
public class BizException extends RuntimeException{
    // 异常码
    private String errorCode;
    // 错误信息
    private String errorMessage;

    public BizException(BaseExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errorMessage = baseExceptionInterface.getErrorMessage();
    }
}
