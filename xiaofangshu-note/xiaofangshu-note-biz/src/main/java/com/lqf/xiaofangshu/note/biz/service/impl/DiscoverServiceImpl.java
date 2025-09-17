package com.lqf.xiaofangshu.note.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lqf.framework.common.response.PageResponse;
import com.lqf.xiaofangshu.note.biz.domain.dataobject.NoteCountDO;
import com.lqf.xiaofangshu.note.biz.domain.dataobject.NoteDO;
import com.lqf.xiaofangshu.note.biz.domain.mapper.NoteCountDOMapper;
import com.lqf.xiaofangshu.note.biz.domain.mapper.NoteDOMapper;
import com.lqf.xiaofangshu.note.biz.enums.NoteTypeEnum;
import com.lqf.xiaofangshu.note.biz.model.vo.FindDiscoverNotePageListReqVO;
import com.lqf.xiaofangshu.note.biz.model.vo.FindDiscoverNoteRspVO;
import com.lqf.xiaofangshu.note.biz.rpc.UserRpcService;
import com.lqf.xiaofangshu.note.biz.service.DiscoverService;
import com.lqf.xiaofangshu.user.dto.resp.FindUserByIdRspDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class DiscoverServiceImpl implements DiscoverService {

    @Resource
    private NoteDOMapper noteDOMapper;
    @Resource
    private NoteCountDOMapper noteCountDOMapper;
    @Resource
    private UserRpcService userRpcService;


    @Override
    public PageResponse<FindDiscoverNoteRspVO> findNoteList(FindDiscoverNotePageListReqVO findDiscoverNoteListReqVO) {
        Long channelId = findDiscoverNoteListReqVO.getChannelId();
        Integer pageNo = findDiscoverNoteListReqVO.getPageNo();

        // 每页展示的数据量
        long pageSize = 10;

        // TODO: 为快速完成前后端联调，目前走数据库，后续需改成查询 Elasticserach

        int count = noteDOMapper.selectTotalCount(channelId);

        // 若评论总数为 0，则直接响应
        if (count == 0) {
            return PageResponse.success(null, pageNo, 0);
        }

        // 计算分页查询的偏移量 offset
        long offset = PageResponse.getOffset(pageNo, pageSize);
        long totalPage = PageResponse.getTotalPage(count, pageSize);

        // 若请求的页码大于总页数，直接响应
        if (pageNo > totalPage) {
            return PageResponse.success(null, pageNo, totalPage);
        }

        List<NoteDO> noteDOS = noteDOMapper.selectPageList(channelId, offset, pageSize);

        List<Long> creatorIds = noteDOS.stream().map(NoteDO::getCreatorId).toList();

        // RPC: 调用用户服务，批量获取用户信息（头像、昵称等）
        List<FindUserByIdRspDTO> findUserByIdRspDTOS = userRpcService.findByIds(creatorIds);
        Map<Long, FindUserByIdRspDTO> userIdAndDTOMap = findUserByIdRspDTOS.stream()
                .collect(Collectors.toMap(FindUserByIdRspDTO::getId, dto -> dto));

        // 批量查询笔记计数 TODO: 快速完成前后端联调，后续需要改成走 RPC 查询
        List<Long> noteIds = noteDOS.stream().map(NoteDO::getId).toList();
        List<NoteCountDO> noteCountDOS = noteCountDOMapper.selectByNoteIds(noteIds);
        Map<Long, NoteCountDO> noteIdAndDOMap = Maps.newHashMap();
        if (CollUtil.isNotEmpty(noteCountDOS)) {
            noteIdAndDOMap = noteCountDOS.stream()
                    .collect(Collectors.toMap(NoteCountDO::getNoteId, noteCountDO -> noteCountDO));
        }

        List<FindDiscoverNoteRspVO> noteRspVOS = Lists.newArrayList();
        // 分页返参
        for (NoteDO noteDO : noteDOS) {
            Integer type = noteDO.getType();
            FindDiscoverNoteRspVO findDiscoverNoteRspVO = FindDiscoverNoteRspVO.builder()
                    .id(String.valueOf(noteDO.getId()))
                    .title(noteDO.getTitle())
                    .type(type)
                    .build();

            NoteTypeEnum noteTypeEnum = NoteTypeEnum.valueOf(type);

            switch (noteTypeEnum) {
                case IMAGE_TEXT -> {
                    // 提取第一张图片作为封面图
                    // String cover = Optional.ofNullable(noteDO.getImgUris())
                    //         .map(uris -> StringUtils.split(uris, ",")[0])
                    //         .orElse(null);
                    String cover = Optional.ofNullable(noteDO.getImgUris())
                            .map(String::trim)
                            .filter(StringUtils::isNotBlank)
                            .flatMap(uris -> Arrays.stream(StringUtils.split(uris, ","))
                                    .filter(StringUtils::isNotBlank)
                                    .findFirst())
                            .orElse(null);
                    findDiscoverNoteRspVO.setCover(cover);
                }
                case VIDEO -> findDiscoverNoteRspVO.setVideoUri(noteDO.getVideoUri());
            }

            // 设置发布者信息
            Long creatorId = noteDO.getCreatorId();
            FindUserByIdRspDTO findUserByIdRspDTO = userIdAndDTOMap.get(creatorId);
            if (Objects.nonNull(findUserByIdRspDTO)) {
                findDiscoverNoteRspVO.setCreatorId(creatorId);
                findDiscoverNoteRspVO.setNickname(findUserByIdRspDTO.getNickName());
                findDiscoverNoteRspVO.setAvatar(findUserByIdRspDTO.getAvatar());
            }

            // 设置点赞数据
            NoteCountDO noteCountDO = noteIdAndDOMap.get(noteDO.getId());
            findDiscoverNoteRspVO.setLikeTotal(Objects.nonNull(noteCountDO)
                    ? String.valueOf(noteCountDO.getLikeTotal()) : "0");

            noteRspVOS.add(findDiscoverNoteRspVO);
        }

        return PageResponse.success(noteRspVOS, pageNo, count, pageSize);
    }
}
