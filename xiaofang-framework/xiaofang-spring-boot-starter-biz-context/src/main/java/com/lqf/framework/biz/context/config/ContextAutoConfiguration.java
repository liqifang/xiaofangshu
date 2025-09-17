package com.lqf.framework.biz.context.config;

import com.lqf.framework.biz.context.filter.HeaderUserId2ContextFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author: 李启仿
 * @date: 2025/6/22
 * @description:
 */
@AutoConfiguration
public class ContextAutoConfiguration {

    @Bean
    public FilterRegistrationBean<HeaderUserId2ContextFilter> filterFilterRegistrationBean() {
        HeaderUserId2ContextFilter filter = new HeaderUserId2ContextFilter();
        return new FilterRegistrationBean<>(filter);
    }
}
