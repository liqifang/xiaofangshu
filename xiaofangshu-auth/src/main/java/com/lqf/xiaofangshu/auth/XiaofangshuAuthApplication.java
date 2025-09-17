package com.lqf.xiaofangshu.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.lqf.xiaofangshu")
public class XiaofangshuAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaofangshuAuthApplication.class, args);
    }

}
