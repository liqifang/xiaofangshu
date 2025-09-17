package com.lqf.xiaofangshu.search.biz.domain.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李启仿
 * @date: 2025/9/2
 * @description:
 */

public interface SelectMapper {

    /**
     * 查询笔记文档所需的全字段数据
     * @param noteId
     * @return
     */
    List<Map<String, Object>> selectEsNoteIndexData(@Param("noteId") Long noteId, @Param("userId") Long userId);

    /**
     * 查询用户索引所需的全字段数据
     * @param userId
     * @return
     */
    List<Map<String, Object>> selectEsUserIndexData(@Param("userId") Long userId);
}