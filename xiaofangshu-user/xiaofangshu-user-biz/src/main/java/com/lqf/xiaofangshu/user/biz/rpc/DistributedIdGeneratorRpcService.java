package com.lqf.xiaofangshu.user.biz.rpc;

import com.lqf.xiaofangshu.distributed.id.generator.api.DistributedIdGeneratorFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author: 李启仿
 * @date: 2025/7/10
 * @description: 分布式ID生成服务
 */

@Component
public class DistributedIdGeneratorRpcService {

    @Resource
    private DistributedIdGeneratorFeignApi distributedIdGeneratorFeignApi;

    /**
     * Leaf 号段模式：小哈书 ID 业务标识
     */
    private static final String BIZ_TAG_XIAOHASHU_ID = "leaf-segment-xiaohashu-id";

    /**
     * Leaf 号段模式：用户 ID 业务标识
     */
    private static final String BIZ_TAG_USER_ID = "leaf-segment-user-id";

    /**
     * 调用分布式 ID 生成服务生成小哈书 ID
     * @return
     */
    public String getXiaohashuId() {
        return distributedIdGeneratorFeignApi.getSegmentId(BIZ_TAG_XIAOHASHU_ID);
    }

    /**
     * 调用分布式 ID 生成服务用户 ID
     *
     * @return
     */
    public String getUserId() {
        return distributedIdGeneratorFeignApi.getSegmentId(BIZ_TAG_USER_ID);
    }
}
