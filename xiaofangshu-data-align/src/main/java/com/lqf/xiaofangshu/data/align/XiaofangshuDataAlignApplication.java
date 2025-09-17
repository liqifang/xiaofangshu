package com.lqf.xiaofangshu.data.align;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: 李启仿
 * @date: 2025/7/22
 * @description:
 */

@SpringBootApplication
@MapperScan("com.lqf.xiaofangshu.data.align.domain.mapper")
@EnableFeignClients(basePackages = "com.lqf.xiaofangshu")
public class XiaofangshuDataAlignApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaofangshuDataAlignApplication.class, args);
    }

}