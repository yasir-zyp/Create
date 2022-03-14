package com.mall4j.cloud.multishop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//获取注册页面信息
import javax.validation.constraints.NotBlank;
@Data
public class AuthDTO {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("电话")
    private String accountPhone;

    @ApiModelProperty("验证码")
    private String code;
}
