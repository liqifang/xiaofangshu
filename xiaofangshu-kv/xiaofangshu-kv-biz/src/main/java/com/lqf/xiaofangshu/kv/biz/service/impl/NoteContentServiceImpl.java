package com.lqf.xiaofangshu.kv.biz.service.impl;

import com.lqf.framework.common.exception.BizException;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.kv.biz.enums.ResponseCodeEnum;
import com.lqf.xiaofangshu.kv.biz.service.NoteContentService;
import com.lqf.xiaofangshu.kv.biz.domain.dataobject.NoteContentDO;
import com.lqf.xiaofangshu.kv.biz.domain.reository.NoteContentRepository;
import com.lqf.xiaofangshu.kv.dto.req.AddNoteContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.req.DeleteNoteContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.req.FindNoteContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.rsp.FindNoteContentRspDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: 李启仿
 * @date: 2025/6/27
 * @description: 笔记内容存储业务
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class NoteContentServiceImpl implements NoteContentService {

    private final NoteContentRepository noteContentRepository;

    /**
     * 删除笔记内容
     *
     * @param deleteNoteContentReqDTO
     * @return
     */
    @Override
    public Response<?> deleteNoteContent(DeleteNoteContentReqDTO deleteNoteContentReqDTO) {
        // 笔记 ID
        String uuid = deleteNoteContentReqDTO.getUuid();
        // 删除笔记内容
        noteContentRepository.deleteById(UUID.fromString(uuid));

        return Response.success();
    }

    @Override
    public Response<FindNoteContentRspDTO> findNoteContent(FindNoteContentReqDTO findNoteContentReqDTO) {
        String noteId = findNoteContentReqDTO.getUuid();

        Optional<NoteContentDO> optional = noteContentRepository.findById(UUID.fromString(noteId));

        if (optional.isEmpty()) {
            throw new BizException(ResponseCodeEnum.NOTE_CONTENT_NOT_FOUND);
        }

        NoteContentDO noteContentDO = optional.get();

        // 构建返参 DTO
        FindNoteContentRspDTO findNoteContentRspDTO = FindNoteContentRspDTO.builder()
                .uuid(noteContentDO.getId())
                .content(noteContentDO.getContent())
                .build();

        return Response.success(findNoteContentRspDTO);
    }

    @Override
    public Response<?> addNoteContent(AddNoteContentReqDTO addNoteContentReqDTO) {
        // 笔记 ID
        String uuid = addNoteContentReqDTO.getUuid();
        // 笔记内容
        String content = addNoteContentReqDTO.getContent();

        // 构建数据库 DO 实体类
        NoteContentDO nodeContent = NoteContentDO.builder()
                .id(UUID.fromString(uuid))
                .content(content)
                .build();

        // 插入数据
        noteContentRepository.save(nodeContent);

        return Response.success();
    }
}