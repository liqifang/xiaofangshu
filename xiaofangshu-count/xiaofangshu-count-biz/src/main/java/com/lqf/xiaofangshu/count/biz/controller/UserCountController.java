package com.lqf.xiaofangshu.count.biz.controller;

import com.lqf.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.count.biz.service.UserCountService;
import com.lqf.xiaofangshu.count.dto.FindUserCountsByIdReqDTO;
import com.lqf.xiaofangshu.count.dto.FindUserCountsByIdRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 李启仿
 * @date: 2025/8/26
 * @description: 用户计数维度
 */

@Slf4j
@RestController
@RequestMapping("/count")
public class UserCountController {

    @Resource
    private UserCountService userCountService;

    @PostMapping(value = "/user/data")
    @ApiOperationLog(description = "获取用户计数数据")
    public Response<FindUserCountsByIdRspDTO> findUserCountData(@Validated @RequestBody FindUserCountsByIdReqDTO findUserCountsByIdReqDTO) {
        return userCountService.findUserCountData(findUserCountsByIdReqDTO);
    }
}
