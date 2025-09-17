package com.lqf.xiaofangshu.comment.biz.rpc;

import com.lqf.xiaofangshu.distributed.id.generator.api.DistributedIdGeneratorFeignApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: 李启仿
 * @date: 2025/8/8
 * @description: 分布式ID服务
 */

@Component
@RequiredArgsConstructor
public class DistributedIdGeneratorRpcService {

    private final DistributedIdGeneratorFeignApi distributedIdGeneratorFeignApi;

    /**
     * 生成评论 ID
     *
     * @return
     */
    public String generateCommentId() {
        return distributedIdGeneratorFeignApi.getSegmentId("leaf-segment-comment-id");
    }

}