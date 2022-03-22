package com.mall4j.cloud.multishop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 店铺详情DTO
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 15:50:25
 */
@Data
public class ShopDetailDTO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店铺id")
    private Long shopId;

    @ApiModelProperty("店铺类型1自营店 2普通店")
    private Integer type;

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

    @Size(max = 30)
    @ApiModelProperty(value = "用户名",required=true)
    private String username;
    @Size(max = 30)

    @Size(max = 64)
    @ApiModelProperty(value = "密码",required=true)
    private String password;

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

    @ApiModelProperty("电话")
    private String accountPhone;

    @ApiModelProperty("验证码")
    private String code;

     @ApiModelProperty("资质信息，可添加多个")
    private List<ShopQualificationDTO> shopQualificationDTOS;
}
