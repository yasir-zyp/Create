package com.mall4j.cloud.multishop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ShopAddrDTO {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("主键")
    private Long shopAddrId;

    @ApiModelProperty("详细地址")
    private String theAddr;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("是否默认地址 1是,0否")
    private Integer isDefault;

    @ApiModelProperty("手机")
    private String mobilePhone;

    @ApiModelProperty("邮编")
    private String postCode;

    @ApiModelProperty("省市区id")
    private int areaId[];
}
