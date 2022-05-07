package com.mall4j.cloud.api.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.api.multishop.vo.ShopAddrVO;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import com.mall4j.cloud.common.vo.BaseVO;
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
public class SpuVO extends BaseVO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("spu id,商品id")
	private Long spuId;

	@ApiModelProperty("品牌ID")
	private Long brandId;

	@ApiModelProperty("分类ID")
	private Long categoryId;

	@ApiModelProperty("店铺分类ID")
	private Long shopCategoryId;

	@ApiModelProperty("店铺id")
	private Long shopId;

	@ApiModelProperty("spu名称")
	private String name;

	@ApiModelProperty("卖点")
	private String sellingPoint;

	@ApiModelProperty("商品介绍主图")
	@JsonSerialize(using = ImgJsonSerializer.class)
	private String mainImgUrl;

	@ApiModelProperty("售价，整数方式保存")
	private Long priceFee;

	@ApiModelProperty("市场价，整数方式保存")
	private Long marketPriceFee;

	@ApiModelProperty("状态 1:enable, 0:disable, -1:deleted")
	private Integer status;

	@ApiModelProperty("商品详情")
	private String detail;

	@ApiModelProperty("sku列表")
	private List<SkuVO> skuList;

	@ApiModelProperty("序号")
	private Integer seq;

	@ApiModelProperty("商品销量")
	private Integer saleNum;

	@ApiModelProperty("店铺名称")
	private String shopName;

	@ApiModelProperty("分类信息")
	private CategoryVO category;

	@ApiModelProperty("服务方案编码")
	private String partyCode;

	@ApiModelProperty("分类标签")
	private List<String> spuCategoryName;

	@ApiModelProperty("地址id")
	private Long addrId;

	@ApiModelProperty("地址")
	private ShopAddrVO shopAddr;


}
