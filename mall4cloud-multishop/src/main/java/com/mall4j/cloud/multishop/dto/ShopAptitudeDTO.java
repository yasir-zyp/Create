package com.mall4j.cloud.multishop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/*
*资质详情DTO
* */
@Data
public class ShopAptitudeDTO {
    @ApiModelProperty("资质ID")
    private  Long shopQualificationId;

    @ApiModelProperty("资质证书名称")
    private String nameQualification;

    @ApiModelProperty("资质证书图片地址")
    private String qualificationUrl;

    @ApiModelProperty("店铺id外键")
    private Integer shopId;

}
