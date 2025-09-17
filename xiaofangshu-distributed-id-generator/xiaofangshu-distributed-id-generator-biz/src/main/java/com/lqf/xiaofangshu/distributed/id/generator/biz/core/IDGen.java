package com.lqf.xiaofangshu.distributed.id.generator.biz.core;


import com.lqf.xiaofangshu.distributed.id.generator.biz.core.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
