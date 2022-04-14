package com.mall4j.cloud.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SmsMessageDTO {


    @ApiModelProperty("电话号码")
    private String accountPhone;

    @ApiModelProperty("系统类型:0.普通用户系统 1.商家端 2平台端")
    private Integer systemType;

}
