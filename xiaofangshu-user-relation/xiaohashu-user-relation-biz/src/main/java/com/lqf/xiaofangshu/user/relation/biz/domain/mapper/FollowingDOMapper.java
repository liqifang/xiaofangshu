package com.lqf.xiaofangshu.user.relation.biz.domain.mapper;

import com.lqf.xiaofangshu.user.relation.biz.domain.dataobject.FollowingDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowingDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FollowingDO record);

    int insertSelective(FollowingDO record);

    FollowingDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FollowingDO record);

    int updateByPrimaryKey(FollowingDO record);

    List<FollowingDO> selectByUserId(Long userId);

    int deleteByUserIdAndFollowingUserId(@Param("userId") Long userId,
                                         @Param("unfollowUserId") Long unfollowUserId);

    /**
     * 查询记录总数
     *
     * @param userId
     * @return
     */
    long selectCountByUserId(Long userId);

    /**
     * 分页查询
     * @param userId 想要查询的用户 ID
     * @param offset 分页查询偏移量
     * @param limit 每页展示多少条数据
     * @return
     */
    List<FollowingDO> selectPageListByUserId(@Param("userId") Long userId,
                                             @Param("offset") long offset,
                                             @Param("limit") long limit);

    /**
     * 查询关注用户列表
     * @param userId
     * @return
     */
    List<FollowingDO> selectAllByUserId(Long userId);
}