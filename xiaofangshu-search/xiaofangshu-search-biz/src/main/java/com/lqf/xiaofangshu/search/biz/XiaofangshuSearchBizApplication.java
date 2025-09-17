package com.lqf.xiaofangshu.search.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: 李启仿
 * @date: 2025/7/11
 * @description:
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.lqf.xiaofangshu.search.biz.domain.mapper")
public class XiaofangshuSearchBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaofangshuSearchBizApplication.class, args);
    }

}