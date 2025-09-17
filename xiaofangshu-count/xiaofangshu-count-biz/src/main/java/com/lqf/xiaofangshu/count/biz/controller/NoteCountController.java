package com.lqf.xiaofangshu.count.biz.controller;

import com.lqf.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.count.biz.service.NoteCountService;
import com.lqf.xiaofangshu.count.dto.FindNoteCountsByIdRspDTO;
import com.lqf.xiaofangshu.count.dto.FindNoteCountsByIdsReqDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 李启仿
 * @date: 2025/8/27
 * @description: 用户计数维度
 */

@Slf4j
@RestController
@RequestMapping("/count")
public class NoteCountController {

    @Resource
    private NoteCountService noteCountService;

    @PostMapping(value = "/notes/data")
    @ApiOperationLog(description = "批量获取笔记计数数据")
    public Response<List<FindNoteCountsByIdRspDTO>> findNotesCountData(@Validated @RequestBody FindNoteCountsByIdsReqDTO findNoteCountsByIdsReqDTO) {
        return noteCountService.findNotesCountData(findNoteCountsByIdsReqDTO);
    }

}