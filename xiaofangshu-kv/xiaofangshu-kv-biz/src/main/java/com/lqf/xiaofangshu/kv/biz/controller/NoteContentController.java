package com.lqf.xiaofangshu.kv.biz.controller;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.kv.biz.service.NoteContentService;
import com.lqf.xiaofangshu.kv.dto.req.AddNoteContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.req.DeleteNoteContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.req.FindNoteContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.rsp.FindNoteContentRspDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 李启仿
 * @date: 2025/6/27
 * @description: 笔记内容
 */

@RestController
@RequestMapping("/kv")
@Slf4j
@RequiredArgsConstructor
public class NoteContentController {

    private final NoteContentService noteContentService;

    @PostMapping(value = "/note/content/add")
    public Response<?> addNoteContent(@Validated @RequestBody AddNoteContentReqDTO addNoteContentReqDTO) {
        return noteContentService.addNoteContent(addNoteContentReqDTO);
    }

    @PostMapping(value = "/note/content/find")
    public Response<FindNoteContentRspDTO> findNoteContent(@Validated @RequestBody FindNoteContentReqDTO findNoteContentReqDTO) {
        return noteContentService.findNoteContent(findNoteContentReqDTO);
    }

    @PostMapping(value = "/note/content/delete")
    public Response<?> deleteNoteContent(@Validated @RequestBody DeleteNoteContentReqDTO deleteNoteContentReqDTO) {
        return noteContentService.deleteNoteContent(deleteNoteContentReqDTO);
    }
}