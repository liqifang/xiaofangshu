package com.lqf.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

/**
 * 日期格式枚举
 */
@Getter
@AllArgsConstructor
public enum DateConstantsEnum {
    DATE_FORMAT_Y_M_D_H_M_S(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
    DATE_FORMAT_Y_M_D(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    DATE_FORMAT_H_M_S(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")),
    DATE_FORMAT_Y_M(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM")),

    /**
     * DateTimeFormatter：月-日
     */
    DATE_FORMAT_M_D(DateTimeFormatter.ofPattern("MM-dd")),
    /**
     * DateTimeFormatter：时：分
     */
    DATE_FORMAT_H_M(DateTimeFormatter.ofPattern("HH:mm"))
    ;

    private final DateTimeFormatter value;

}
