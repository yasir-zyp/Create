package com.mall4j.cloud.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * spu信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Data
public class SpuPageSearchDTO {

	@ApiModelProperty("spuId")
	private Long spuId;

	@ApiModelProperty("分类IDs")
	private List<Long> categoryIds;

	@ApiModelProperty("服务方案名称")
	private String name;
	@ApiModelProperty("服务方案类型0:样品检测，1:现场检测")
	private String serviceType;
	@ApiModelProperty("商品介绍主图")
	private String mainImgUrl;
	@ApiModelProperty("检测产品名")
	private String sellingPoint;
	@ApiModelProperty("样品要求")
	private String  sampleReques;
	@ApiModelProperty("检测周期(工作日) ")
	private Integer cycle;

	@ApiModelProperty("状态 1:enable, 0:disable, -1:deleted")
	private Integer status;

	@ApiModelProperty("spuId列表(商品上下架：批量操作时，用此参数)(批量处理参数)")
	private List<Long> spuIds;

	@ApiModelProperty("商品状态： 0.全部  1.销售中   2.已下架 ")
	private Integer spuStatus;

	@ApiModelProperty("最低价")
	private Long minPrice;

	@ApiModelProperty("最高价")
	private Long maxPrice;

	@ApiModelProperty("最低销量")
	private Long minSaleNum;

	@ApiModelProperty("最高销量")
	private Long maxSaleNum;

	@ApiModelProperty("服务方案编码")
	private String partyCode;

	@ApiModelProperty("当前价排序 0：倒序 1：顺序")
	private Integer priceFeeSort;

	@ApiModelProperty("市场价排序 0：倒序 1：顺序")
	private Integer marketPriceFeeSort;

	@ApiModelProperty("销量排序 0：倒序 1：顺序")
	private Integer saleNumSort;

	@ApiModelProperty("序号排序 0：倒序 1：顺序")
	private Integer seqSort;

	@ApiModelProperty("创建时间排序 0：倒序 1：顺序")
	private Integer createTimeSort;
	@ApiModelProperty("送样地址id")
	private Long deliverAddressId;
}
