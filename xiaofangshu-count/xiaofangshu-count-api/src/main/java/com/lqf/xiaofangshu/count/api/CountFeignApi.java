package com.lqf.xiaofangshu.count.api;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.count.constant.ApiConstants;
import com.lqf.xiaofangshu.count.dto.FindNoteCountsByIdRspDTO;
import com.lqf.xiaofangshu.count.dto.FindNoteCountsByIdsReqDTO;
import com.lqf.xiaofangshu.count.dto.FindUserCountsByIdReqDTO;
import com.lqf.xiaofangshu.count.dto.FindUserCountsByIdRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: 李启仿
 * @date: 2025/8/26
 * @description: 计数服务 Feign 接口
 */

@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface CountFeignApi {

    String PREFIX = "/count";

    /**
     * 查询用户计数
     *
     * @param findUserCountsByIdReqDTO
     * @return
     */
    @PostMapping(value = PREFIX + "/user/data")
    Response<FindUserCountsByIdRspDTO> findUserCount(@RequestBody FindUserCountsByIdReqDTO findUserCountsByIdReqDTO);

    /**
     * 批量查询笔记计数
     *
     * @param findNoteCountsByIdsReqDTO
     * @return
     */
    @PostMapping(value = PREFIX + "/notes/data")
    Response<List<FindNoteCountsByIdRspDTO>> findNotesCount(@RequestBody FindNoteCountsByIdsReqDTO findNoteCountsByIdsReqDTO);
}