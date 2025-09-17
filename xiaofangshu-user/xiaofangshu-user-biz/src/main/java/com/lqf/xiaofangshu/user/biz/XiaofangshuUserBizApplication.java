package com.lqf.xiaofangshu.user.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.lqf.xiaofangshu.user.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.lqf.xiaofangshu")
public class XiaofangshuUserBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaofangshuUserBizApplication.class, args);
    }

}
