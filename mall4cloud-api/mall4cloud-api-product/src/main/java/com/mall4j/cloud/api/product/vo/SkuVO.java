package com.mall4j.cloud.api.product.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * sku信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Data
public class SkuVO extends BaseVO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("属性id")
	private Long skuId;

	@ApiModelProperty("SPU id")
	private Long spuId;

	@ApiModelProperty("多个销售属性值id逗号分隔")
	private String attrs;

	@ApiModelProperty("sku名称")
	private String skuName;


	@ApiModelProperty("售价，整数方式保存")
	private Long priceFee;

	@ApiModelProperty("市场价，整数方式保存")
	private Long marketPriceFee;

	@ApiModelProperty("状态 1:enable, 0:disable, -1:deleted")
	private Integer status;


	@ApiModelProperty("商品编码")
	private String partyCode;



	@ApiModelProperty("当前sku检测项列表")
	private List<SkuAttrVO> skuAttrs;


}
