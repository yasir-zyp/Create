package com.mall4j.cloud.auth.dto;

import com.mall4j.cloud.api.auth.constant.SysTypeEnum;
import lombok.Data;

@Data
public class AuthAccountDTO  {
    /**
     * 用户在自己系统的用户id
     */
    private Long userId;

    /*
    * 用户名
    * */
    protected String username;
    /*
     * 密码
     * */
    protected String password;
    /**
     * 全局唯一的id,
     */
    private Long uid;

    /**
     * 租户id (商家id)
     */
    private Long tenantId;

    /**
     * 系统类型
     * @see SysTypeEnum
     */
    private Integer sysType;

    /**
     * 是否是管理员
     */
    private Integer isAdmin;

    private String bizUserId;

    private String bizUid;

}
