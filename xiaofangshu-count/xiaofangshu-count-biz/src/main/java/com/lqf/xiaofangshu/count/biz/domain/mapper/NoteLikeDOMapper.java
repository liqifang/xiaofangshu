package com.lqf.xiaofangshu.count.biz.domain.mapper;

import com.lqf.xiaofangshu.count.biz.domain.dataobject.NoteLikeDO;

public interface NoteLikeDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NoteLikeDO record);

    int insertSelective(NoteLikeDO record);

    NoteLikeDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NoteLikeDO record);

    int updateByPrimaryKey(NoteLikeDO record);
}