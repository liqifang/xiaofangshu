package com.lqf.xiaofangshu.kv.biz.domain.reository;

import com.lqf.xiaofangshu.kv.biz.domain.dataobject.NoteContentDO;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

/**
 * @author: 李启仿
 * @date: 2025/6/27
 * @description:
 */

public interface NoteContentRepository extends CassandraRepository<NoteContentDO, UUID> {

}
