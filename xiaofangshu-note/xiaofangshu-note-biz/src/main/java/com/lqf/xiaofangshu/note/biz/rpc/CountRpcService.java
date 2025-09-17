package com.lqf.xiaofangshu.note.biz.rpc;

import cn.hutool.core.collection.CollUtil;
import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.count.api.CountFeignApi;
import com.lqf.xiaofangshu.count.dto.FindNoteCountsByIdRspDTO;
import com.lqf.xiaofangshu.count.dto.FindNoteCountsByIdsReqDTO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author: 李启仿
 * @date: 2025/8/27
 * @description: 计数服务
 */

@Component
public class CountRpcService {

    @Resource
    private CountFeignApi countFeignApi;

    /**
     * 批量查询笔记计数
     *
     * @param noteIds
     * @return
     */
    public List<FindNoteCountsByIdRspDTO> findByNoteIds(List<Long> noteIds) {
        FindNoteCountsByIdsReqDTO findNoteCountsByIdsReqDTO = new FindNoteCountsByIdsReqDTO();
        findNoteCountsByIdsReqDTO.setNoteIds(noteIds);

        Response<List<FindNoteCountsByIdRspDTO>> response = countFeignApi.findNotesCount(findNoteCountsByIdsReqDTO);

        if (!response.isSuccess() || Objects.isNull(response.getData()) || CollUtil.isEmpty(response.getData())) {
            return null;
        }

        return response.getData();
    }

}