package com.mall4j.cloud.multishop.model;

import java.io.Serializable;

import com.mall4j.cloud.common.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 店铺详情
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 15:50:25
 */
@Data
public class ShopDetail extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺类型1自营店 2普通店
     */
    private Integer type;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺简介
     */
    private String intro;

    /**
     * 店铺logo(可修改)
     */
    private String shopLogo;

    /**
     * 店铺状态(-1:已删除 0: 停业中 1:营业中)
     */
    private Integer shopStatus;

    /**
     * 营业执照
     */
    private String businessLicense;

    /**
     * 身份证正面
     */
    private String identityCardFront;

    /**
     * 身份证反面
     */
    private String identityCardLater;

	/**
	 * 移动端背景图
	 */
	private String mobileBackgroundPic;

	/**
	 * pc背景图
	 */
	private String pcBackgroundPic;

	/*法人代表*/
	private String legalRepresentative;

	/*统一社会信用代码*/
	private String creditCode;

	/*业务范围*/
	private String businessScope;

	/*联系方式（座机）*/
	private String landlinePhone;

	/*区*/
	private String area;

	/*城市*/
	private String city;

	/*省*/
	private String province;

	/*区ID*/
	private Integer areaId;

	/*城市ID*/
	private Integer cityId;

	/*省ID*/
	private Integer provinceId;

}
