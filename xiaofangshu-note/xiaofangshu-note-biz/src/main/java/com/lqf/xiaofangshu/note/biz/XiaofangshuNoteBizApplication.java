package com.lqf.xiaofangshu.note.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: 李启仿
 * @date: 2025/7/11
 * @description:
 */
@SpringBootApplication
@MapperScan("com.lqf.xiaofangshu.note.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.lqf.xiaofangshu")
public class XiaofangshuNoteBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaofangshuNoteBizApplication.class, args);
    }

}