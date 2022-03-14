package com.mall4j.cloud.multishop.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.api.vo.search.SpuSearchVO;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 店铺详情VO
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 15:50:25
 */
@Data
public class ShopDetailAppVO extends BaseVO{
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
	@JsonSerialize(using = ImgJsonSerializer.class)
    private String shopLogo;

    @ApiModelProperty("店铺状态(-1:未开通 0: 停业中 1:营业中)")
    private Integer shopStatus;

    @ApiModelProperty("营业执照")
	@JsonSerialize(using = ImgJsonSerializer.class)
    private String businessLicense;

    @ApiModelProperty("身份证正面")
	@JsonSerialize(using = ImgJsonSerializer.class)
    private String identityCardFront;

    @ApiModelProperty("身份证反面")
	@JsonSerialize(using = ImgJsonSerializer.class)
    private String identityCardLater;

    @ApiModelProperty("商品列表")
    private List<SpuSearchVO> spuList;

}
