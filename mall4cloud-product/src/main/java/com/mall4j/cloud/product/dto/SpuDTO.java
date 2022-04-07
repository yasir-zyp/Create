package com.mall4j.cloud.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * spu信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Data
public class SpuDTO{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("spuId,id不显示")
	private Long spuId;

	@ApiModelProperty("分类IDs，查询分类信息,不需要点到第三级，将点到的最后一级的categoryId放进来")
	private List<Long> categoryIds;


	@NotNull(message = "服务方案名称不能为空")
	@ApiModelProperty("spu名称")
	private String name;

	@ApiModelProperty("检测产品名称")
	private String sellingPoint;


	@NotNull(message = "商品主图不能为空")
	@ApiModelProperty("商品主图")
	private String mainImgUrl;

	@ApiModelProperty("市场价")
	private Long marketPriceFee;

	@NotNull(message = "售价不能为空")
	@ApiModelProperty("售价")
	private Long priceFee;

	@ApiModelProperty("状态 1:enable, 0:disable, -1:deleted")
	private Integer status;

	@NotEmpty(message = "sku信息不能为空")
	@ApiModelProperty("商品规格列表")
	private List<SkuDTO> skuList;

	@ApiModelProperty("商品详情")
	private String detail;

	@ApiModelProperty("序号")
	private Integer seq;

	@ApiModelProperty("商品的上下架使用,spuId列表(商品上下架：批量操作时，用此参数)(批量处理参数)")
	private List<Long> spuIds;

	@ApiModelProperty("不需要,店铺id")
	private Long shopId;


}
