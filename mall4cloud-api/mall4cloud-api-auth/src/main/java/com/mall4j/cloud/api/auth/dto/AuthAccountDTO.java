package com.mall4j.cloud.api.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author FrozenWatermelon
 * @date 2020/9/22
 */
@Data
public class AuthAccountDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "username not blank")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建ip
     */
    private String createIp;

    /**
     * 状态 1:启用 0:禁用 -1:删除
     */
    @NotNull(message = "status not null")
    private Integer status;

    /**
     * 系统类型见SysTypeEnum 0.普通用户系统 1.商家端
     */
    @NotNull(message = "sysType not null")
    private Integer sysType;

    /**
     * 用户id
     */
    @NotNull(message = "userId not null")
    private Long userId;

    /**
     * 所属租户
     */
    @NotNull(message = "tenantId not null")
    private Long tenantId;

    /**
     * 是否是管理员
     */
    @NotNull(message = "isAdmin not null")
    private Integer isAdmin;
    /**
     * 手机号
     */
    private String accountPhone;
    /**
     * 微信平台唯一id
     */
    private String unionId;
}
