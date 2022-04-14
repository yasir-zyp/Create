package com.mall4j.cloud.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品sku销售属性关联信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Data
public class SkuAttrDTO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("商品sku销售属性关联信息id")
	private Long spuSkuAttrId;

	@ApiModelProperty("SPU ID")
	private Long spuId;

	@ApiModelProperty("SKU ID")
	private Long skuId;

	@ApiModelProperty("销售属性ID")
	private Long attrId;

	@ApiModelProperty("销售属性名称")
	private String attrName;

	@ApiModelProperty("规格属性描述")
	private String attrDesc;

	@ApiModelProperty("检测项实际价格，整数方式保存")
	private Long priceFee;

	@ApiModelProperty("售价")
	private Long price;

	/*@ApiModelProperty("销售属性值ID")
	private Long attrValueId;

	@ApiModelProperty("销售属性值")
	private String attrValueName;*/

	@ApiModelProperty("状态 1:enable, 0:disable")
	private Integer status;
}
