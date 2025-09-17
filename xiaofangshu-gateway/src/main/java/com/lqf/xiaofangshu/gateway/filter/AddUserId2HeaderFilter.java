package com.lqf.xiaofangshu.gateway.filter;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.lqf.framework.common.constant.GlobalConstants.USER_ID;

/**
 * @author: 李启仿
 * @date: 2025/6/19
 * @description: 转发请求时，将用户 ID 添加到 Header 请求头中，透传给下游服务
 */

@Component
@Slf4j
@Order(-90)
public class AddUserId2HeaderFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("==================> TokenConvertFilter");

        // 用户 ID
        Long userId = null;
        try {
            // 获取当前登录用户的 ID
            userId = StpUtil.getLoginIdAsLong();
        } catch (Exception e) {
            // 若没有登录，则直接放行
            return chain.filter(exchange);
        }

        log.info("## 当前登录的用户 ID: {}", userId);

        Long finalUserId = userId;
        ServerWebExchange newExchange = exchange.mutate()
                .request(builder -> builder.header(USER_ID, String.valueOf(finalUserId))) // 将用户 ID 设置到请求头中
                .build();

        // TODO
        // System.out.println(exchange.getRequest().getHeaders().get(USER_ID));
        return chain.filter(newExchange);
    }
}

