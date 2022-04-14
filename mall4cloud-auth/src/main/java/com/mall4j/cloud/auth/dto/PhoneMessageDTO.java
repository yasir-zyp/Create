package com.mall4j.cloud.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PhoneMessageDTO {
    @ApiModelProperty("电话号码")
    private String accountPhone;


    @ApiModelProperty("验证码")
    private String code;

    @NotNull(message = "sysType不能为空")
    @ApiModelProperty(value = "系统类型 0.普通用户系统 1.商家端", required = true)
    protected Integer sysType;

}
