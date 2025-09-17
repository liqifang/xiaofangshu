package com.lqf.xiaofangshu.user.dto.req;

import com.lqf.framework.common.validator.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 李启仿
 * @date: 2025/6/26
 * @description: 根据手机号查询用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindUserByPhoneReqDTO {

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @PhoneNumber
    private String phone;

}