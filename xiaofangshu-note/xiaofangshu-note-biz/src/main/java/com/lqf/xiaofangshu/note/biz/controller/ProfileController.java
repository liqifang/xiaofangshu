package com.lqf.xiaofangshu.note.biz.controller;


import com.lqf.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lqf.framework.common.response.PageResponse;
import com.lqf.xiaofangshu.note.biz.model.vo.FindProfileNotePageListReqVO;
import com.lqf.xiaofangshu.note.biz.model.vo.FindProfileNoteRspVO;
import com.lqf.xiaofangshu.note.biz.service.ProfileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 犬小哈
 * @date: 2024/4/4 13:22
 * @version: v1.0.0
 * @description: 个人主页
 **/
@RestController
@RequestMapping("/profile")
@Slf4j
public class ProfileController {

    @Resource
    private ProfileService profileService;

    @PostMapping(value = "/note/list")
    @ApiOperationLog(description = "个人主页-查询笔记列表")
    public PageResponse<FindProfileNoteRspVO> findNoteList(@Validated @RequestBody FindProfileNotePageListReqVO findProfileNotePageListReqVO) {
        return profileService.findNoteList(findProfileNotePageListReqVO);
    }

}
