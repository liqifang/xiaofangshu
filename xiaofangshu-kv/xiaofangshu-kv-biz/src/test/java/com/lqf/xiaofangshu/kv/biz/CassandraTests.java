package com.lqf.xiaofangshu.kv.biz;

import com.lqf.framework.common.util.JsonUtils;
import com.lqf.xiaofangshu.kv.biz.domain.dataobject.NoteContentDO;
import com.lqf.xiaofangshu.kv.biz.domain.reository.NoteContentRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

/**
 * @author: 李启仿
 * @date: 2025/6/27
 * @description:
 */

@SpringBootTest
@Slf4j
class CassandraTests {

    @Resource
    private NoteContentRepository noteContentRepository;

    /**
     * 测试插入数据
     */
    @Test
    void testInsert() {
        NoteContentDO nodeContent = NoteContentDO.builder()
                .id(UUID.fromString("a6d2a88d-4235-4c36-a5dd-49631069c520"))
                .content("代码测试笔记内容更新")
                .build();

        noteContentRepository.save(nodeContent);
    }

    /**
     * 测试查询数据
     */
    @Test
    void testSelect() {
        Optional<NoteContentDO> optional = noteContentRepository.findById(UUID.fromString("a6d2a88d-4235-4c36-a5dd-49631069c520"));
        optional.ifPresent(noteContentDO -> log.info("查询结果：{}", JsonUtils.toJsonString(noteContentDO)));
    }

    /**
     * 测试删除数据
     */
    @Test
    void testDelete() {
        noteContentRepository.deleteById(UUID.fromString("a6d2a88d-4235-4c36-a5dd-49631069c520"));
    }

}