package com.lqf.xiaofangshu.kv.api;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.kv.constant.ApiConstants;
import com.lqf.xiaofangshu.kv.dto.req.*;
import com.lqf.xiaofangshu.kv.dto.rsp.FindCommentContentRspDTO;
import com.lqf.xiaofangshu.kv.dto.rsp.FindNoteContentRspDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: 李启仿
 * @date: 2025/6/27
 * @description: K-V 键值存储 Feign 接口
 */

@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface KeyValueFeignApi {

    String PREFIX = "/kv";

    @PostMapping(value = PREFIX + "/note/content/add")
    Response<?> addNoteContent(@RequestBody AddNoteContentReqDTO addNoteContentReqDTO);

    @PostMapping(value = PREFIX + "/note/content/find")
    Response<FindNoteContentRspDTO> findNoteContent(@RequestBody FindNoteContentReqDTO findNoteContentReqDTO);

    @PostMapping(value = PREFIX + "/note/content/delete")
    Response<?> deleteNoteContent(@RequestBody DeleteNoteContentReqDTO deleteNoteContentReqDTO);

    @PostMapping(value = PREFIX + "/comment/content/batchAdd")
    Response<?> batchAddCommentContent(@RequestBody BatchAddCommentContentReqDTO batchAddCommentContentReqDTO);

    @PostMapping(value = PREFIX + "/comment/content/batchFind")
    Response<List<FindCommentContentRspDTO>> batchFindCommentContent(@RequestBody BatchFindCommentContentReqDTO batchFindCommentContentReqDTO);

    @PostMapping(value = PREFIX + "/comment/content/delete")
    Response<?> deleteCommentContent(@RequestBody DeleteCommentContentReqDTO deleteCommentContentReqDTO);

}