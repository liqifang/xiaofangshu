package com.lqf.framework.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
/**
 * @author: 李启仿
 * @date: 2025/6/11
 * @description: 自定义校验类
 */

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        // 这里进行一些初始化操作
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        // 校验逻辑：正则表达式判断手机号是否为 11 位数字
        return phoneNumber != null && phoneNumber.matches("\\d{11}");
    }
}
