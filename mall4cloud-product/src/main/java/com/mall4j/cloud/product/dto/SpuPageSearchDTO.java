package com.mall4j.cloud.product.dto;

import com.mall4j.cloud.api.product.vo.SkuVO;
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

	@ApiModelProperty("spuId,id不显示,商品id 备注：服务方案批量发布（撤回）时使用")
	private Long spuId;

	@ApiModelProperty("spuId列表 备注：服务方案批量发布（撤回）时使用")
	private List<Long> spuIds;

	@ApiModelProperty("不需要")
	private Long shopId;

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

	@ApiModelProperty("服务方案状态： 0.全部  1.销售中   2.已下架 备注：服务方案批量发布（撤回）时使用 ")
	private Integer status;

	@ApiModelProperty("状态")
	private Integer spuStatus;

	@ApiModelProperty("最低价")
	private Long minPrice;

	@ApiModelProperty("最高价")
	private Long maxPrice;

	@ApiModelProperty("送样地址id,不显示,查询地址信息们，将选择的地址id塞进来,")
	private Long deliverAddressId;

	@ApiModelProperty("不需要,最低销量")
	private Long minSaleNum;

	@ApiModelProperty("不需要,最高销量")
	private Long maxSaleNum;

	@ApiModelProperty("不需要,服务方案编码")
	private String partyCode;

	@ApiModelProperty("不需要,当前价排序 0：倒序 1：顺序")
	private Integer priceFeeSort;

	@ApiModelProperty("不需要，市场价排序 0：倒序 1：顺序")
	private Integer marketPriceFeeSort;

	@ApiModelProperty("不需要，销量排序 0：倒序 1：顺序")
	private Integer saleNumSort;

	@ApiModelProperty("不需要，序号排序 0：倒序 1：顺序")
	private Integer seqSort;

	@ApiModelProperty("不需要，创建时间排序 0：倒序 1：顺序")
	private Integer createTimeSort;

	@ApiModelProperty("sku列表")
	private List<SkuVO> skuList;

	private static final long serialVersionUID = 1L;
}
