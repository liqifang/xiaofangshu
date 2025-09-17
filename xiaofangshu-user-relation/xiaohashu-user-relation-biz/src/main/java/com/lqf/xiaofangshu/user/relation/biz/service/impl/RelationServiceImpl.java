package com.lqf.xiaofangshu.user.relation.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.lqf.framework.biz.context.holder.LoginUserContextHolder;
import com.lqf.framework.common.exception.BizException;
import com.lqf.framework.common.response.PageResponse;
import com.lqf.framework.common.response.Response;
import com.lqf.framework.common.util.DateUtils;
import com.lqf.framework.common.util.JsonUtils;
import com.lqf.xiaofangshu.user.dto.resp.FindUserByIdRspDTO;
import com.lqf.xiaofangshu.user.relation.biz.constant.MQConstants;
import com.lqf.xiaofangshu.user.relation.biz.constant.RedisKeyConstants;
import com.lqf.xiaofangshu.user.relation.biz.domain.dataobject.FansDO;
import com.lqf.xiaofangshu.user.relation.biz.domain.dataobject.FollowingDO;
import com.lqf.xiaofangshu.user.relation.biz.domain.mapper.FansDOMapper;
import com.lqf.xiaofangshu.user.relation.biz.domain.mapper.FollowingDOMapper;
import com.lqf.xiaofangshu.user.relation.biz.enums.LuaResultEnum;
import com.lqf.xiaofangshu.user.relation.biz.enums.ResponseCodeEnum;
import com.lqf.xiaofangshu.user.relation.biz.model.dto.FollowUserMqDTO;
import com.lqf.xiaofangshu.user.relation.biz.model.dto.UnfollowUserMqDTO;
import com.lqf.xiaofangshu.user.relation.biz.model.vo.*;
import com.lqf.xiaofangshu.user.relation.biz.rpc.UserRpcService;
import com.lqf.xiaofangshu.user.relation.biz.service.RelationService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author: 李启仿
 * @date: 2025/7/14
 * @description: 关注业务
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class RelationServiceImpl implements RelationService {

    private final UserRpcService userRpcService;
    private final FollowingDOMapper followingDOMapper;
    private final RocketMQTemplate rocketMQTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final FansDOMapper fansDOMapper;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 关注用户
     *
     * @param followUserReqVO
     * @return
     */
    @Override
    public Response<?> follow(FollowUserReqVO followUserReqVO) {
        // 关注的用户 ID
        Long followUserId = followUserReqVO.getFollowUserId();

        // 当前登录的用户 ID
        Long userId = LoginUserContextHolder.getUserId();

        // 校验：无法关注自己
        if (Objects.equals(userId, followUserId)) {
            throw new BizException(ResponseCodeEnum.CANT_FOLLOW_YOUR_SELF);
        }

        // 校验关注的用户是否存在
        FindUserByIdRspDTO findUserByIdRspDTO = userRpcService.findById(followUserId);
        if (Objects.isNull(findUserByIdRspDTO)) {
            throw new BizException(ResponseCodeEnum.FOLLOW_USER_NOT_EXISTED);
        }

        // 构建当前用户关注列表的 Redis Key
        String followingRedisKey = RedisKeyConstants.buildUserFollowingKey(userId);

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        // Lua 脚本路径
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/follow_check_and_add.lua")));
        // 返回值类型
        script.setResultType(Long.class);

        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        // 当前时间转时间戳
        long timestamp = DateUtils.localDateTime2Timestamp(now);

        // 执行 Lua 脚本，拿到返回结果
        Long result = redisTemplate.execute(script, Collections.singletonList(followingRedisKey), followUserId, timestamp);

        // 校验 Lua 脚本执行结果
        checkLuaScriptResult(result);

        // ZSET 不存在
        if (Objects.equals(result, LuaResultEnum.ZSET_NOT_EXIST.getCode())) {
            // 从数据库查询当前用户的关注关系记录
            List<FollowingDO> followingDOS = followingDOMapper.selectByUserId(userId);

            // 随机过期时间
            // 保底1天+随机秒数
            long expireSeconds = 60 * 60 * 24 + RandomUtil.randomInt(60 * 60 * 24);

            // 若记录为空，直接 ZADD 对象, 并设置过期时间
            if (CollUtil.isEmpty(followingDOS)) {
                DefaultRedisScript<Long> script2 = new DefaultRedisScript<>();
                script2.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/follow_add_and_expire.lua")));
                script2.setResultType(Long.class);

                // 可以根据用户类型，设置不同的过期时间，若当前用户为大V, 则可以过期时间设置的长些或者不设置过期时间；如不是，则设置的短些
                // 如何判断呢？可以从计数服务获取用户的粉丝数，目前计数服务还没创建，则暂时采用统一的过期策略
                redisTemplate.execute(script2, Collections.singletonList(followingRedisKey), followUserId, timestamp, expireSeconds);
            } else { // 若记录不为空，则将关注关系数据全量同步到 Redis 中，并设置过期时间；
                // 构建 Lua 参数
                Object[] luaArgs = buildLuaArgs(followingDOS, expireSeconds);

                // 执行 Lua 脚本，批量同步关注关系数据到 Redis 中
                DefaultRedisScript<Long> script3 = new DefaultRedisScript<>();
                script3.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/follow_batch_add_and_expire.lua")));
                script3.setResultType(Long.class);
                redisTemplate.execute(script3, Collections.singletonList(followingRedisKey), luaArgs);

                // 再次调用上面的 Lua 脚本：follow_check_and_add.lua , 将最新的关注关系添加进去
                result = redisTemplate.execute(script, Collections.singletonList(followingRedisKey), followUserId, timestamp);
                checkLuaScriptResult(result);
            }
        }

        // 发送 MQ
        // 首先，构建消息体 DTO 实体类；
        FollowUserMqDTO followUserMqDTO = FollowUserMqDTO.builder()
                .followUserId(followUserId)
                .userId(userId)
                .createTime(LocalDateTime.now())
                .build();

        // 构建 Message 消息对象，并将 DTO 转成 Json 字符串设置到消息体中；
        Message<String> message = MessageBuilder.withPayload(JsonUtils.toJsonString(followUserMqDTO))
                .build();

        // 构建顺序发送消息的hashKey
        String hashKey = String.valueOf(userId);

        // 通过冒号 : 连接, 携带上标签 Tag 的名称；
        String destination = MQConstants.TOPIC_FOLLOW_OR_UNFOLLOW + ":" + MQConstants.TAG_FOLLOW;
        // asyncSend() 是异步发送 MQ, SendCallback 为回调方法，提供了发送成功和异常两个方法；
        rocketMQTemplate.asyncSendOrderly(destination, message, hashKey, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("==> MQ 发送成功，SendResult: {}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("==> MQ 发送异常: ", throwable);
            }
        });

        return Response.success();
    }

    /**
     * 校验 Lua 脚本结果，根据状态码抛出对应的业务异常
     * @param result
     */
    private static void checkLuaScriptResult(Long result) {
        LuaResultEnum luaResultEnum = LuaResultEnum.valueOf(result);

        if (Objects.isNull(luaResultEnum)) throw new RuntimeException("Lua 返回结果错误");
        // 校验 Lua 脚本执行结果
        switch (luaResultEnum) {
            // 关注数已达到上限
            case FOLLOW_LIMIT -> throw new BizException(ResponseCodeEnum.FOLLOWING_COUNT_LIMIT);
            // 已经关注了该用户
            case ALREADY_FOLLOWED -> throw new BizException(ResponseCodeEnum.ALREADY_FOLLOWED);
        }
    }

    /**
     * 构建 Lua 脚本参数
     *
     * @param followingDOS
     * @param expireSeconds
     * @return
     */
    private static Object[] buildLuaArgs(List<FollowingDO> followingDOS, long expireSeconds) {
        int argsLength = followingDOS.size() * 2 + 1; // 每个关注关系有 2 个参数（score 和 value），再加一个过期时间
        Object[] luaArgs = new Object[argsLength];

        int i = 0;
        for (FollowingDO following : followingDOS) {
            luaArgs[i] = DateUtils.localDateTime2Timestamp(following.getCreateTime()); // 关注时间作为 score
            luaArgs[i + 1] = following.getFollowingUserId();          // 关注的用户 ID 作为 ZSet value
            i += 2;
        }

        luaArgs[argsLength - 1] = expireSeconds; // 最后一个参数是 ZSet 的过期时间
        return luaArgs;
    }

    /**
     * 取关用户
     *
     * @param unfollowUserReqVO
     * @return
     */
    @Override
    public Response<?> unfollow(UnfollowUserReqVO unfollowUserReqVO) {
        // 想要取关了用户 ID
        Long unfollowUserId = unfollowUserReqVO.getUnfollowUserId();
        // 当前登录用户 ID
        Long userId = LoginUserContextHolder.getUserId();

        // 无法取关自己
        if (Objects.equals(userId, unfollowUserId)) {
            throw new BizException(ResponseCodeEnum.CANT_UNFOLLOW_YOUR_SELF);
        }

        // 校验关注的用户是否存在
        FindUserByIdRspDTO findUserByIdRspDTO = userRpcService.findById(unfollowUserId);

        if (Objects.isNull(findUserByIdRspDTO)) {
            throw new BizException(ResponseCodeEnum.FOLLOW_USER_NOT_EXISTED);
        }

        // 当前用户的关注列表 Redis Key
        String followingRedisKey = RedisKeyConstants.buildUserFollowingKey(userId);

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        // Lua 脚本路径
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/unfollow_check_and_delete.lua")));
        // 返回值类型
        script.setResultType(Long.class);

        // 执行 Lua 脚本，拿到返回结果
        Long result = redisTemplate.execute(script, Collections.singletonList(followingRedisKey), unfollowUserId);

        // 校验 Lua 脚本执行结果
        // 取关的用户不在关注列表中
        if (Objects.equals(result, LuaResultEnum.NOT_FOLLOWED.getCode())) {
            throw new BizException(ResponseCodeEnum.NOT_FOLLOWED);
        }

        if (Objects.equals(result, LuaResultEnum.ZSET_NOT_EXIST.getCode())) { // ZSET 关注列表不存在
            // 从数据库查询当前用户的关注关系记录
            List<FollowingDO> followingDOS = followingDOMapper.selectByUserId(userId);

            // 随机过期时间
            // 保底1天+随机秒数
            long expireSeconds = 60*60*24 + RandomUtil.randomInt(60*60*24);

            // 若记录为空，则表示还未关注任何人，提示还未关注对方
            if (CollUtil.isEmpty(followingDOS)) {
                throw new BizException(ResponseCodeEnum.NOT_FOLLOWED);
            } else { // 若记录不为空，则将关注关系数据全量同步到 Redis 中，并设置过期时间；
                // 构建 Lua 参数
                Object[] luaArgs = buildLuaArgs(followingDOS, expireSeconds);

                // 执行 Lua 脚本，批量同步关注关系数据到 Redis 中
                DefaultRedisScript<Long> script3 = new DefaultRedisScript<>();
                script3.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/follow_batch_add_and_expire.lua")));
                script3.setResultType(Long.class);
                redisTemplate.execute(script3, Collections.singletonList(followingRedisKey), luaArgs);

                // 再次调用上面的 Lua 脚本：unfollow_check_and_delete.lua , 将取关的用户删除
                result = redisTemplate.execute(script, Collections.singletonList(followingRedisKey), unfollowUserId);
                // 再次校验结果
                if (Objects.equals(result, LuaResultEnum.NOT_FOLLOWED.getCode())) {
                    throw new BizException(ResponseCodeEnum.NOT_FOLLOWED);
                }
            }
        }

        // 发送 MQ
        // 构建消息体 DTO
        UnfollowUserMqDTO unfollowUserMqDTO = UnfollowUserMqDTO.builder()
                .userId(userId)
                .unfollowUserId(unfollowUserId)
                .createTime(LocalDateTime.now())
                .build();

        // 构建消息对象，并将 DTO 转成 Json 字符串设置到消息体中
        Message<String> message = MessageBuilder.withPayload(JsonUtils.toJsonString(unfollowUserMqDTO))
                .build();

        // 构建hashKey
        String hashKey = String.valueOf(userId);

        // 通过冒号连接, 可让 MQ 发送给主题 Topic 时，携带上标签 Tag
        String destination = MQConstants.TOPIC_FOLLOW_OR_UNFOLLOW + ":" + MQConstants.TAG_UNFOLLOW;

        log.info("==> 开始发送取关操作 MQ, 消息体: {}", unfollowUserMqDTO);

        // 异步发送 MQ 消息，提升接口响应速度
        rocketMQTemplate.asyncSendOrderly(destination, message, hashKey, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("==> MQ 发送成功，SendResult: {}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("==> MQ 发送异常: ", throwable);
            }
        });

        return Response.success();
    }

    /**
     * 查询关注列表
     * @param findFollowingListReqVO
     * @return
     */
    @Override
    public PageResponse<FindFollowingUserRspVO> findFollowingList(FindFollowingListReqVO findFollowingListReqVO) {
        // 获取查询的用户id
        Long userId = findFollowingListReqVO.getUserId();
        // 获取页码
        Integer pageNo = findFollowingListReqVO.getPageNo();

        // 优先从 Redis 中查询，判断目标用户的关注列表 ZSet 的总大小；
        String followingListRedisKey = RedisKeyConstants.buildUserFollowingKey(userId);
        Long total = redisTemplate.opsForZSet().zCard(followingListRedisKey);

        // 返参
        List<FindFollowingUserRspVO> findFollowingUserRspVOS = null;

        // 每页展示 10 条数据
        long limit = 10;

        // 若大小大于 0， 则说明缓存中有数据：
        if (total > 0) {
            // 计算一共多少页
            long totalPage = PageResponse.getTotalPage(total, limit);

            // 请求的页码超出了总页数
            if (pageNo > totalPage) return PageResponse.success(null, pageNo, total);

            // 准备从 Redis 中查询 ZSet 分页数据
            // 每页 10 个元素，计算偏移量
            long offset = (pageNo - 1) * limit;

            // 使用 ZREVRANGEBYSCORE 命令按 score 降序获取元素，同时使用 LIMIT 子句实现分页
            // 注意：这里使用了 Double.POSITIVE_INFINITY 和 Double.NEGATIVE_INFINITY 作为分数范围
            // 因为关注列表最多有 1000 个元素，这样可以确保获取到所有的元素
            Set<Object> followingUserIdsSet = redisTemplate.opsForZSet()
                    .reverseRangeByScore(followingListRedisKey, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, offset, limit);

            if (CollUtil.isNotEmpty(followingUserIdsSet)) {
                // 提取所有用户 ID 到集合中
                List<Long> userIds = followingUserIdsSet.stream().map(object -> Long.valueOf(object.toString())).toList();

                // RPC: 批量查询用户信息
                List<FindUserByIdRspDTO> findUserByIdRspDTOS = userRpcService.findByIds(userIds);

                // 若不为空，DTO 转 VO
                if (CollUtil.isNotEmpty(findUserByIdRspDTOS)) {
                    findFollowingUserRspVOS = findUserByIdRspDTOS.stream()
                            .map(dto -> FindFollowingUserRspVO.builder()
                                    .userId(dto.getId())
                                    .avatar(dto.getAvatar())
                                    .nickname(dto.getNickName())
                                    .introduction(dto.getIntroduction())
                                    .build())
                            .toList();
                }
            }
        } else {
            // 若 Redis 中没有数据，则从数据库查询
            // 通过 selectCountByUserId() 查询关注列表总量；
            long count = followingDOMapper.selectCountByUserId(userId);

            long totalPage = PageResponse.getTotalPage(count, pageNo);
            // 判断请求的页码是否超出了总页数；
            // 请求的页码超出了总页数
            if (pageNo > totalPage) return PageResponse.success(null, pageNo, count);

            // 若没有，计算查询页码的偏移量，
            long offset = PageResponse.getOffset(pageNo, limit);

            // 分页查询
            List<FollowingDO> followingDOS = followingDOMapper.selectPageListByUserId(userId, offset, limit);

            // 赋值真实的记录总数
            total = count;

            // 若记录不为空
            if (CollUtil.isNotEmpty(followingDOS)) {
                // 提取所有关注用户 ID 到集合中;
                List<Long> userIds = followingDOS.stream().map(FollowingDO::getFollowingUserId).toList();

                // RPC: 调用用户服务，并将 DTO 转换为 VO
                findFollowingUserRspVOS = rpcUserServiceAndDTO2VO(userIds, findFollowingUserRspVOS);

                // 异步将关注列表全量同步到 Redis 中；
                threadPoolTaskExecutor.submit(() -> syncFollowingList2Redis(userId));
            }
            // 返回分页结果；

        }
        return PageResponse.success(findFollowingUserRspVOS, pageNo, total);
    }

    /**
     * 全量同步关注列表至 Redis 中
     */
    private void syncFollowingList2Redis(Long userId) {
        // 查询全量关注用户列表（1000位用户）
        List<FollowingDO> followingDOS = followingDOMapper.selectAllByUserId(userId);
        if (CollUtil.isNotEmpty(followingDOS)) {
            // 用户关注列表 Redis Key
            String followingListRedisKey = RedisKeyConstants.buildUserFollowingKey(userId);
            // 随机过期时间
            // 保底1天+随机秒数
            long expireSeconds = 60*60*24 + RandomUtil.randomInt(60*60*24);
            // 构建 Lua 参数
            Object[] luaArgs = buildLuaArgs(followingDOS, expireSeconds);

            // 执行 Lua 脚本，批量同步关注关系数据到 Redis 中
            DefaultRedisScript<Long> script = new DefaultRedisScript<>();
            script.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/follow_batch_add_and_expire.lua")));
            script.setResultType(Long.class);
            redisTemplate.execute(script, Collections.singletonList(followingListRedisKey), luaArgs);
        }
    }

    /**
     * RPC: 调用用户服务，并将 DTO 转换为 VO
     * @param userIds
     * @param findFollowingUserRspVOS
     * @return
     */
    private List<FindFollowingUserRspVO> rpcUserServiceAndDTO2VO(List<Long> userIds, List<FindFollowingUserRspVO> findFollowingUserRspVOS) {
        // RPC: 批量查询用户信息
        List<FindUserByIdRspDTO> findUserByIdRspDTOS = userRpcService.findByIds(userIds);

        // 若不为空，DTO 转 VO
        if (CollUtil.isNotEmpty(findUserByIdRspDTOS)) {
            findFollowingUserRspVOS = findUserByIdRspDTOS.stream()
                    .map(dto -> FindFollowingUserRspVO.builder()
                            .userId(dto.getId())
                            .avatar(dto.getAvatar())
                            .nickname(dto.getNickName())
                            .introduction(dto.getIntroduction())
                            .build())
                    .toList();
        }
        return findFollowingUserRspVOS;
    }

    /**
     * 查询关注列表
     * @param findFansListReqVO
     * @return
     */
    @Override
    public PageResponse<FindFansUserRspVO> findFansList(FindFansListReqVO findFansListReqVO) {
        // 想要查询的用户 ID
        Long userId = findFansListReqVO.getUserId();
        // 页码
        Integer pageNo = findFansListReqVO.getPageNo();

        // 先从 Redis 中查询
        String fansListRedisKey = RedisKeyConstants.buildUserFansKey(userId);

        // 查询目标用户粉丝列表 ZSet 的总大小
        long total = redisTemplate.opsForZSet().zCard(fansListRedisKey);

        // 返参
        List<FindFansUserRspVO> findFansUserRspVOS = null;

        // 每页展示 10 条数据
        long limit = 10;

        if (total > 0) { // 缓存中有数据
            // 计算一共多少页
            long totalPage = PageResponse.getTotalPage(total, limit);

            // 请求的页码超出了总页数
            if (pageNo > totalPage) return PageResponse.success(null, pageNo, total);

            // 准备从 Redis 中查询 ZSet 分页数据
            // 每页 10 个元素，计算偏移量
            long offset = PageResponse.getOffset(pageNo, limit);

            // 使用 ZREVRANGEBYSCORE 命令按 score 降序获取元素，同时使用 LIMIT 子句实现分页
            Set<Object> followingUserIdsSet = redisTemplate.opsForZSet()
                    .reverseRangeByScore(fansListRedisKey, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, offset, limit);

            if (CollUtil.isNotEmpty(followingUserIdsSet)) {
                // 提取所有用户 ID 到集合中
                List<Long> userIds = followingUserIdsSet.stream().map(object -> Long.valueOf(object.toString())).toList();

                // RPC: 批量查询用户信息
                findFansUserRspVOS = rpcUserServiceAndCountServiceAndDTO2VO(userIds, findFansUserRspVOS);
            }
        } else { // 若 Redis 缓存中无数据，则查询数据库
            // 先查询记录总量
            total = fansDOMapper.selectCountByUserId(userId);

            // 计算一共多少页
            long totalPage = PageResponse.getTotalPage(total, limit);

            // 请求的页码超出了总页数（只允许查询前 500 页）
            if (pageNo > 500 || pageNo > totalPage) return PageResponse.success(null, pageNo, total);

            // 偏移量
            long offset = PageResponse.getOffset(pageNo, limit);

            // 分页查询
            List<FansDO> fansDOS = fansDOMapper.selectPageListByUserId(userId, offset, limit);

            // 若记录不为空
            if (CollUtil.isNotEmpty(fansDOS)) {
                // 提取所有粉丝用户 ID 到集合中
                List<Long> userIds = fansDOS.stream().map(FansDO::getFansUserId).toList();

                // RPC: 调用用户服务、计数服务，并将 DTO 转换为 VO
                findFansUserRspVOS = rpcUserServiceAndCountServiceAndDTO2VO(userIds, findFansUserRspVOS);

                // 异步将粉丝列表同步到 Redis（最多5000条）
                threadPoolTaskExecutor.submit(() -> syncFansList2Redis(userId));
            }
        }

        return PageResponse.success(findFansUserRspVOS, pageNo, total);
    }

    /**
     * 粉丝列表同步到 Redis（最多5000条）
     * @param userId
     */
    private void syncFansList2Redis(Long userId) {
        // 查询粉丝列表（最多5000位用户）
        List<FansDO> fansDOS = fansDOMapper.select5000FansByUserId(userId);
        if (CollUtil.isNotEmpty(fansDOS)) {
            // 用户粉丝列表 Redis Key
            String fansListRedisKey = RedisKeyConstants.buildUserFansKey(userId);
            // 随机过期时间
            // 保底1天+随机秒数
            long expireSeconds = 60*60*24 + RandomUtil.randomInt(60*60*24);
            // 构建 Lua 参数
            Object[] luaArgs = buildFansZSetLuaArgs(fansDOS, expireSeconds);

            // 执行 Lua 脚本，批量同步关注关系数据到 Redis 中
            DefaultRedisScript<Long> script = new DefaultRedisScript<>();
            script.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/follow_batch_add_and_expire.lua")));
            script.setResultType(Long.class);
            redisTemplate.execute(script, Collections.singletonList(fansListRedisKey), luaArgs);
        }
    }

    /**
     * 构建 Lua 脚本参数：粉丝列表
     * @param fansDOS
     * @param expireSeconds
     * @return
     */
    private static Object[] buildFansZSetLuaArgs(List<FansDO> fansDOS, long expireSeconds) {
        int argsLength = fansDOS.size() * 2 + 1; // 每个粉丝关系有 2 个参数（score 和 value），再加一个过期时间
        Object[] luaArgs = new Object[argsLength];

        int i = 0;
        for (FansDO fansDO : fansDOS) {
            luaArgs[i] = DateUtils.localDateTime2Timestamp(fansDO.getCreateTime()); // 粉丝的关注时间作为 score
            luaArgs[i + 1] = fansDO.getFansUserId();          // 粉丝的用户 ID 作为 ZSet value
            i += 2;
        }

        luaArgs[argsLength - 1] = expireSeconds; // 最后一个参数是 ZSet 的过期时间
        return luaArgs;
    }

    /**
     * RPC: 调用用户服务、计数服务，并将 DTO 转换为 VO 粉丝列表
     * @param userIds
     * @param findFansUserRspVOS
     * @return
     */
    private List<FindFansUserRspVO> rpcUserServiceAndCountServiceAndDTO2VO(List<Long> userIds, List<FindFansUserRspVO> findFansUserRspVOS) {
        // RPC: 批量查询用户信息
        List<FindUserByIdRspDTO> findUserByIdRspDTOS = userRpcService.findByIds(userIds);

        // TODO RPC: 批量查询用户的计数数据（笔记总数、粉丝总数）

        // 若不为空，DTO 转 VO
        if (CollUtil.isNotEmpty(findUserByIdRspDTOS)) {
            findFansUserRspVOS = findUserByIdRspDTOS.stream()
                    .map(dto -> FindFansUserRspVO.builder()
                            .userId(dto.getId())
                            .avatar(dto.getAvatar())
                            .nickname(dto.getNickName())
                            .noteTotal(0L) // TODO: 这块的数据暂无，后续补充
                            .fansTotal(0L) // TODO: 这块的数据暂无，后续补充
                            .build())
                    .toList();
        }
        return findFansUserRspVOS;
    }
}