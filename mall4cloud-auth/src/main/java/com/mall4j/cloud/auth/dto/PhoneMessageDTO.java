package com.mall4j.cloud.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PhoneMessageDTO {
    @ApiModelProperty("电话号码")
    private String accountPhone;


    @ApiModelProperty("验证码")
    private String code;
}
