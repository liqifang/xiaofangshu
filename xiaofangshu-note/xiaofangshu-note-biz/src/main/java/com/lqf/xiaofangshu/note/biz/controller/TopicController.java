package com.lqf.xiaofangshu.note.biz.controller;


import com.lqf.framework.biz.operationlog.aspect.ApiOperationLog;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.note.biz.model.vo.FindTopicListReqVO;
import com.lqf.xiaofangshu.note.biz.model.vo.FindTopicRspVO;
import com.lqf.xiaofangshu.note.biz.service.TopicService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 犬小哈
 * @date: 2024/4/4 13:22
 * @version: v1.0.0
 * @description: 话题
 **/
@RestController
@RequestMapping("/topic")
@Slf4j
public class TopicController {

    @Resource
    private TopicService topicService;

    @PostMapping(value = "/list")
    @ApiOperationLog(description = "模糊查询话题列表")
    public Response<List<FindTopicRspVO>> findTopicList(@Validated @RequestBody FindTopicListReqVO findTopicListReqVO) {
        return topicService.findTopicList(findTopicListReqVO);
    }

}
