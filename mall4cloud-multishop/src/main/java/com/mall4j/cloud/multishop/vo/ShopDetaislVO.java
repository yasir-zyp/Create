package com.mall4j.cloud.multishop.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class ShopDetaislVO extends BaseVO {

    @ApiModelProperty("主键")
    private Long shopId;

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

    @ApiModelProperty("店铺类型1自营店 2普通店")
    private Integer type;

    /*区ID*/
    private Integer areaIds;

    /*城市ID*/
    private Integer cityId;

    /*省ID*/
    private Integer provinceId;

    @ApiModelProperty("省市区id")
    private int areaId[];

    private static final long serialVersionUID = 1L;
}
