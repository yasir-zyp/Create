package com.mall4j.cloud.multishop.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShopQualificationVO {

    @ApiModelProperty("主键")
    private  Long shopQualificationId;

    @ApiModelProperty("资质证书名称")
    private String nameQualification;


    @ApiModelProperty("资质证书图片地址")
    private String qualificationUrl;


    @ApiModelProperty("资质状态")
    private Integer status;


    @ApiModelProperty("资质外键")
    private Integer shopId;
}
