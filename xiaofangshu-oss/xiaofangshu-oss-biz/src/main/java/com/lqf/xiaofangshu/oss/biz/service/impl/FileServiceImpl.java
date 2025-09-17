package com.lqf.xiaofangshu.oss.biz.service.impl;

import com.lqf.framework.common.response.Response;
import com.lqf.xiaofangshu.oss.biz.strategy.FileStrategy;
import com.lqf.xiaofangshu.oss.biz.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 李启仿
 * @date: 2025/6/23
 * @description: TODO
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileStrategy fileStrategy;

    private static final String BUCKET_NAME = "xiaohashu";

    @Override
    public Response<?> uploadFile(MultipartFile file) {
        // 上传文件
        String url = fileStrategy.uploadFile(file, BUCKET_NAME);

        return Response.success(url);
    }
}