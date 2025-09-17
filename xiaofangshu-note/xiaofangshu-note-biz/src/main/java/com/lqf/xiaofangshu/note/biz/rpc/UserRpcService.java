package com.lqf.xiaofangshu.note.biz.rpc;

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
import java.util.stream.Collectors;

/**
 * @author: 李启仿
 * @date: 2025/7/12
 * @description: 用户服务
 */

@Component
@RequiredArgsConstructor
public class UserRpcService {

    private final UserFeignApi userFeignApi;

    /**
     * 查询用户信息
     * @param userId 用户ID
     * @return FindUserByIdRspDTO
     */
    public FindUserByIdRspDTO findById(Long userId) {
        FindUserByIdReqDTO findUserByIdReqDTO = new FindUserByIdReqDTO();
        findUserByIdReqDTO.setId(userId);

        Response<FindUserByIdRspDTO> response = userFeignApi.findById(findUserByIdReqDTO);

        if (Objects.isNull(response) || !response.isSuccess()) {
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
        if (CollUtil.isEmpty(userIds)) {
            return null;
        }

        FindUsersByIdsReqDTO findUsersByIdsReqDTO = new FindUsersByIdsReqDTO();
        // 去重, 并设置用户 ID 集合
        findUsersByIdsReqDTO.setIds(userIds.stream().distinct().collect(Collectors.toList()));

        Response<List<FindUserByIdRspDTO>> response = userFeignApi.findByIds(findUsersByIdsReqDTO);

        if (!response.isSuccess() || Objects.isNull(response.getData()) || CollUtil.isEmpty(response.getData())) {
            return null;
        }

        return response.getData();
    }
}
