package com.lqf.xiaofangshu.note.biz.rpc;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.kv.api.KeyValueFeignApi;
import com.lqf.xiaofangshu.kv.dto.req.AddNoteContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.req.DeleteNoteContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.req.FindNoteContentReqDTO;
import com.lqf.xiaofangshu.kv.dto.rsp.FindNoteContentRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: 李启仿
 * @date: 2025/7/11
 * @description: KV 键值服务
 */

@Component
@RequiredArgsConstructor
public class KeyValueRpcService {

    private final KeyValueFeignApi keyValueFeignApi;

    /**
     * 查询笔记内容
     *
     * @param uuid 笔记UUID
     * @return 笔记内容
     */
    public String findNoteContent(String uuid) {
        FindNoteContentReqDTO findNoteContentReqDTO = new FindNoteContentReqDTO();
        findNoteContentReqDTO.setUuid(uuid);

        Response<FindNoteContentRspDTO> response = keyValueFeignApi.findNoteContent(findNoteContentReqDTO);

        if (Objects.isNull(response) || !response.isSuccess() || Objects.isNull(response.getData())) {
            return null;
        }

        return response.getData().getContent();
    }

    /**
     * 保存笔记内容
     *
     * @param uuid
     * @param content
     * @return
     */
    public boolean saveNoteContent(String uuid, String content) {
        AddNoteContentReqDTO addNoteContentReqDTO = new AddNoteContentReqDTO();
        addNoteContentReqDTO.setUuid(uuid);
        addNoteContentReqDTO.setContent(content);

        Response<?> response = keyValueFeignApi.addNoteContent(addNoteContentReqDTO);

        if (Objects.isNull(response) || !response.isSuccess()) {
            return false;
        }

        return true;
    }

    /**
     * 删除笔记内容
     *
     * @param uuid
     * @return
     */
    public boolean deleteNoteContent(String uuid) {
        DeleteNoteContentReqDTO deleteNoteContentReqDTO = new DeleteNoteContentReqDTO();
        deleteNoteContentReqDTO.setUuid(uuid);

        Response<?> response = keyValueFeignApi.deleteNoteContent(deleteNoteContentReqDTO);

        if (Objects.isNull(response) || !response.isSuccess()) {
            return false;
        }

        return true;
    }

}
