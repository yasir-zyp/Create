package com.mall4j.cloud.product.vo.app;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * spu信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Data
public class SpuAppVO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("spu id")
	private Long spuId;

	@ApiModelProperty("店铺id")
	private Long shopId;

	@ApiModelProperty("spu名称")
	private String name;

	@ApiModelProperty("卖点")
	private String sellingPoint;

	@JsonSerialize(using = ImgJsonSerializer.class)
	@ApiModelProperty("商品介绍主图")
	private String mainImgUrl;


	@ApiModelProperty("售价，整数方式保存")
	private Long priceFee;

	@ApiModelProperty("市场价，整数方式保存")
	private Long marketPriceFee;

	@ApiModelProperty("商品详情")
	private String detail;


	@ApiModelProperty("sku列表")
	private List<SkuAppVO> skus;

	@ApiModelProperty("商品销量")
	private Integer saleNum;
}
