package com.lqf.xiaofangshu.search.biz.service;

import org.springframework.http.ResponseEntity;

/**
 * @author: 李启仿
 * @date: 2025/8/5
 * @description: 拓展词典
 */

public interface ExtDictService {

    /**
     * 获取热更新词典
     * @return
     */
    ResponseEntity<String> getHotUpdateExtDict();
}