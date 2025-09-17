package com.lqf.xiaofangshu.comment.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author: 李启仿
 * @date: 2025/7/11
 * @description:
 */

@EnableRetry // 启用 Spring Retry
@SpringBootApplication
@EnableFeignClients(basePackages = "com.lqf.xiaofangshu")
@MapperScan("com.lqf.xiaofangshu.comment.biz.domain.mapper")
public class XiaofangshuCommentBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaofangshuCommentBizApplication.class, args);
    }

}