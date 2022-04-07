package com.mall4j.cloud.multishop.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GatheringInfoVO{

    @ApiModelProperty("主键")
    private Long gatheringInfoId;

    @ApiModelProperty("支付类型（0支付宝，1微信，2银行卡）")
    private Long paymentType;

    @ApiModelProperty("账号")
    private String theAccount;

    @ApiModelProperty("户名")
    private String accountName;

    @ApiModelProperty("开户行")
    private String openingBank;

    private static final long serialVersionUID = 1L;
}