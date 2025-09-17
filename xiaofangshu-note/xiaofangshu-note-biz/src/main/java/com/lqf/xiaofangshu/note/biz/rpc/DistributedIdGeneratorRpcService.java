package com.lqf.xiaofangshu.note.biz.rpc;

import com.lqf.xiaofangshu.distributed.id.generator.api.DistributedIdGeneratorFeignApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: 李启仿
 * @date: 2025/7/11
 * @description: 用户服务
 */

@Component
@RequiredArgsConstructor
public class DistributedIdGeneratorRpcService {

    private final DistributedIdGeneratorFeignApi distributedIdGeneratorFeignApi;

    /**
     * 生成雪花算法 ID
     *
     * @return
     */
    public String getSnowflakeId() {
        return distributedIdGeneratorFeignApi.getSnowflakeId("test");
    }
}
