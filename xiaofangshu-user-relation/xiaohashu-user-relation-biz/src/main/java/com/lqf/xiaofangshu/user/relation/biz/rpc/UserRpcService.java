package com.lqf.xiaofangshu.user.relation.biz.rpc;

import cn.hutool.core.collection.CollUtil;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.user.api.UserFeignApi;
import com.lqf.xiaofangshu.user.dto.req.FindUserByIdReqDTO;
import com.lqf.xiaofangshu.user.dto.req.FindUsersByIdsReqDTO;
import com.lqf.xiaofangshu.user.dto.resp.FindUserByIdRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author: 李启仿
 * @date: 2025/7/14
 * @description: 用户服务
 */

@Component
@RequiredArgsConstructor
public class UserRpcService {

    private final UserFeignApi userFeignApi;

    /**
     * 根据用户 ID 查询
     *
     * @param userId
     * @return
     */
    public FindUserByIdRspDTO findById(Long userId) {
        FindUserByIdReqDTO findUserByIdReqDTO = new FindUserByIdReqDTO();
        findUserByIdReqDTO.setId(userId);

        Response<FindUserByIdRspDTO> response = userFeignApi.findById(findUserByIdReqDTO);

        if (!response.isSuccess() || Objects.isNull(response.getData())) {
            return null;
        }

        return response.getData();
    }

    /**
     * 批量查询用户信息
     *
     * @param userIds
     * @return
     */
    public List<FindUserByIdRspDTO> findByIds(List<Long> userIds) {
        FindUsersByIdsReqDTO findUsersByIdsReqDTO = new FindUsersByIdsReqDTO();
        findUsersByIdsReqDTO.setIds(userIds);

        Response<List<FindUserByIdRspDTO>> response = userFeignApi.findByIds(findUsersByIdsReqDTO);

        if (!response.isSuccess() || Objects.isNull(response.getData()) || CollUtil.isEmpty(response.getData())) {
            return null;
        }

        return response.getData();
    }
}
