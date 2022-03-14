package com.mall4j.cloud.multishop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
/*
* 添加基本信息
* */
@Data
public class BasicInformationDTO {
    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("店铺简介")
    private String intro;

    @ApiModelProperty("店铺logo(可修改)")
    private String shopLogo;

    @ApiModelProperty("店铺状态(-1:已删除 0: 停业中 1:营业中)")
    private Integer shopStatus;

    @ApiModelProperty("营业执照")
    private String businessLicense;

    @ApiModelProperty("身份证正面")
    private String identityCardFront;

    @ApiModelProperty("身份证反面")
    private String identityCardLater;

    @ApiModelProperty("移动端背景图")
    @NotBlank(message="移动端背景图不能为空")
    private String mobileBackgroundPic;

    @ApiModelProperty("法人代表")
    private String legalRepresentative;

    @ApiModelProperty("统一社会信用代码")
    private String creditCode;

    @ApiModelProperty("业务范围")
    private String businessScope;

    @ApiModelProperty("联系方式（座机）")
    private String landlinePhone;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("区ID,页面不显示")
    private Integer areaId;

    @ApiModelProperty("城市ID，页面不显示")
    private Integer cityId;

    @ApiModelProperty("省ID，页面不显示")
    private Integer provinceId;
}
