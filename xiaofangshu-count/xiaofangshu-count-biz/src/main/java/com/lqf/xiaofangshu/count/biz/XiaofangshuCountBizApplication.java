package com.lqf.xiaofangshu.count.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: 李启仿
 * @date: 2025/7/22
 * @description:
 */

@SpringBootApplication
@MapperScan("com.lqf.xiaofangshu.count.biz.domain.mapper")
// @EnableFeignClients
public class XiaofangshuCountBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaofangshuCountBizApplication.class, args);
    }

}