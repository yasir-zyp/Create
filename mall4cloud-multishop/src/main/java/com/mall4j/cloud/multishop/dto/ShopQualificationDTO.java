package com.mall4j.cloud.multishop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/*
*资质详情DTO
* */
@Data
public class ShopQualificationDTO {
    @ApiModelProperty("主键")
    private  Long shopQualificationId;

    @ApiModelProperty("资质证书名称")
    private String nameQualification;

    @ApiModelProperty("资质证书图片地址")
    private String qualificationUrl;
}
