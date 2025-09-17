package com.lqf.framework.biz.operationlog.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiOperationLog {
    /**
     * @description: API 功能描述
     * @return
     */
    String description() default "";

}