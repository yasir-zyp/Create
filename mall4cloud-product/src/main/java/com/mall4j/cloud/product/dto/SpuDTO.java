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

	@ApiModelProperty("spuId")
	private Long spuId;

	@ApiModelProperty("品牌ID")
	private Long brandId;

	@NotNull(message = "分类不能为空")
	@ApiModelProperty("分类ID")
	private Long categoryId;

	@NotNull(message = "店铺分类不能为空")
	@ApiModelProperty("店铺分类ID")
	private Long shopCategoryId;

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

	@ApiModelProperty("spuId列表(商品上下架：批量操作时，用此参数)(批量处理参数)")
	private List<Long> spuIds;

	@ApiModelProperty("店铺id")
	private Long shopId;


}
