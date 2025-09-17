package com.lqf.xiaofangshu.user.relation.biz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.lqf.xiaofangshu.user.relation.biz.domain.mapper")
@EnableFeignClients(basePackages = "com.lqf.xiaofangshu")
public class XiaofangshuUserRelationBizApplication  {

    public static void main(String[] args) {
        SpringApplication.run(XiaofangshuUserRelationBizApplication.class, args);
    }

}
