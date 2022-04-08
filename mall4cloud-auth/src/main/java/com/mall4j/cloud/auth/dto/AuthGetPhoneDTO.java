package com.mall4j.cloud.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuthGetPhoneDTO {
    @ApiModelProperty(value = "微信信息errMsg")
    protected String errMsg;
    @ApiModelProperty(value = "encryptedData")
    protected String encryptedData;
    @ApiModelProperty(value = "iv")
    protected String iv;
    @ApiModelProperty(value = "cloudID")
    protected String cloudID;
    @ApiModelProperty(value = "code")
    protected String code;
    @ApiModelProperty(value = "unionId")
    protected String unionId;

}
