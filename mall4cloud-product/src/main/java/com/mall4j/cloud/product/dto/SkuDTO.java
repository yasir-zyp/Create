package com.mall4j.cloud.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * sku信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Data
public class SkuDTO{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("属性id主id，id不显示")
	private Long skuId;

	@ApiModelProperty("SPU id，不需要")
	private Long spuId;

	@ApiModelProperty("多个销售属性值id逗号分隔")
	private String attrs;

	@ApiModelProperty("检测服务方案,sku名称")
	private String skuName;

	@ApiModelProperty("售价，整数方式保存")
	private Long priceFee;

	@ApiModelProperty("市场价，整数方式保存")
	private Long marketPriceFee;

	@ApiModelProperty("状态 1:enable, 0:disable, -1:deleted")
	private Integer status;

	@ApiModelProperty("检测项列表,备注:查询检测项，可多选中检测项")
	private List<SkuAttrDTO> skuAttrs;

}
