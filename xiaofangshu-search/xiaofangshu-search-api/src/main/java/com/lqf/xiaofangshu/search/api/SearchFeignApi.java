package com.lqf.xiaofangshu.search.api;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.search.constant.ApiConstants;
import com.lqf.xiaofangshu.search.dto.RebuildNoteDocumentReqDTO;
import com.lqf.xiaofangshu.search.dto.RebuildUserDocumentReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: 李启仿
 * @date: 2025/9/3
 * @description:
 */

@FeignClient(name = ApiConstants.SERVICE_NAME)
public interface SearchFeignApi {

    String PREFIX = "/search";

    /**
     * 重建笔记文档
     * @param rebuildNoteDocumentReqDTO
     * @return
     */
    @PostMapping(value = PREFIX + "/note/document/rebuild")
    Response<?> rebuildNoteDocument(@RequestBody RebuildNoteDocumentReqDTO rebuildNoteDocumentReqDTO);


    /**
     * 重建用户文档
     * @param rebuildUserDocumentReqDTO
     * @return
     */
    @PostMapping(value = PREFIX + "/user/document/rebuild")
    Response<?> rebuildUserDocument(@RequestBody RebuildUserDocumentReqDTO rebuildUserDocumentReqDTO);

}
