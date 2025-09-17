package com.lqf.xiaofangshu.note.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.note.biz.domain.dataobject.ChannelDO;
import com.lqf.xiaofangshu.note.biz.domain.mapper.ChannelDOMapper;
import com.lqf.xiaofangshu.note.biz.model.vo.FindChannelRspVO;
import com.lqf.xiaofangshu.note.biz.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelDOMapper channelDOMapper;

    /**
     * 查询所有频道
     *
     * @return
     */
    @Override
    public Response<List<FindChannelRspVO>> findChannelList() {
        // TODO: 加二级缓存

        List<ChannelDO> channelDOS = channelDOMapper.selectAll();

        List<FindChannelRspVO> channelRspVOS = Lists.newArrayList();

        // 默认添加一个 “全部” 分类
        // FindChannelRspVO allChannel = FindChannelRspVO.builder()
        //         .id(0L)
        //         .name("全部")
        //         .build();
        // channelRspVOS.add(allChannel);

        if (CollUtil.isNotEmpty(channelDOS)) {
            CollUtil.addAll(channelRspVOS, channelDOS.stream()
                    .map(channelDO -> FindChannelRspVO.builder()
                            .id(channelDO.getId())
                            .name(channelDO.getName())
                            .build())
                    .toList());
        }

        return Response.success(channelRspVOS);
    }
}
