package com.mall4j.cloud.api.product.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品sku销售属性关联信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Data
public class SkuAttrVO extends BaseVO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("商品sku销售属性关联信息id")
	private Integer skuAttrId;

	@ApiModelProperty("SPU ID")
	private Long spuId;

	@ApiModelProperty("SKU ID")
	private Long skuId;

	@ApiModelProperty("检测项ID")
	private Long attrId;

	@ApiModelProperty("检测项名称")
	private String attrName;

	@ApiModelProperty("检测周期(工作日)")
	private Integer cycle;
	/**
	 * 标准号
	 */
	@ApiModelProperty("标准号")
	private String standard;

	@ApiModelProperty("标准名称")
	private String standardName;

	@ApiModelProperty("售价")
	private Long priceFee;

	@ApiModelProperty("售价")
	private Long price;
	@ApiModelProperty("状态 1:enable, 0:disable, -1:deleted")
	private Integer status;
}
