package com.mall4j.cloud.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SmsMessageDTO {
    @ApiModelProperty("电话号码")
    private String accountPhone;
}
