package com.lqf.xiaofangshu.note.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.note.biz.domain.dataobject.TopicDO;
import com.lqf.xiaofangshu.note.biz.domain.mapper.TopicDOMapper;
import com.lqf.xiaofangshu.note.biz.model.vo.FindTopicListReqVO;
import com.lqf.xiaofangshu.note.biz.model.vo.FindTopicRspVO;
import com.lqf.xiaofangshu.note.biz.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicDOMapper topicDOMapper;

    @Override
    public Response<List<FindTopicRspVO>> findTopicList(FindTopicListReqVO findTopicListReqVO) {
        String keyword = findTopicListReqVO.getKeyword();

        List<TopicDO> topicDOS = topicDOMapper.selectByLikeName(keyword);

        List<FindTopicRspVO> findTopicRspVOS = null;
        if (CollUtil.isNotEmpty(topicDOS)) {
            findTopicRspVOS = topicDOS.stream()
                    .map(topicDO -> FindTopicRspVO.builder()
                            .id(topicDO.getId())
                            .name(topicDO.getName())
                            .build())
                    .toList();
        }

        return Response.success(findTopicRspVOS);
    }
}
