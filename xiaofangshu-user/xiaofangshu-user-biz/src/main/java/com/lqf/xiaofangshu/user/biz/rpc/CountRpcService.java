package com.lqf.xiaofangshu.user.biz.rpc;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.count.api.CountFeignApi;
import com.lqf.xiaofangshu.count.dto.FindUserCountsByIdReqDTO;
import com.lqf.xiaofangshu.count.dto.FindUserCountsByIdRspDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: 李启仿
 * @date: 2025/8/26
 * @description: 计数服务
 */

@Component
public class CountRpcService {

    @Resource
    private CountFeignApi countFeignApi;

    /**
     * 查询用户计数信息
     * @param userId
     * @return
     */
    public FindUserCountsByIdRspDTO findUserCountById(Long userId) {
        FindUserCountsByIdReqDTO findUserCountsByIdReqDTO = new FindUserCountsByIdReqDTO();
        findUserCountsByIdReqDTO.setUserId(userId);

        Response<FindUserCountsByIdRspDTO> response = countFeignApi.findUserCount(findUserCountsByIdReqDTO);

        if (Objects.isNull(response) || !response.isSuccess()) {
            return null;
        }

        return response.getData();
    }

}