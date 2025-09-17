package com.lqf.xiaofangshu.search.biz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: 李启仿
 * @date: 2025/8/3
 * @description: Elasticsearch配置
 */

@ConfigurationProperties(prefix = "elasticsearch")
@Component
@Data
public class ElasticsearchProperties {
    private String address;
}