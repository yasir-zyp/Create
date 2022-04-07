package com.mall4j.cloud.api.product.vo;

import com.mall4j.cloud.common.vo.BaseVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 属性信息VO
 *
 * @author zyp
 * @date 2022-3-10 15:27:23
 */
@Data
public class AttrVO extends BaseVO{
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("attr id")
	private Long attrId;

	@ApiModelProperty("店铺id")
	private Long shopId;

	@ApiModelProperty("分类id")
	private Long categoryId;

	@ApiModelProperty("分类名称")
	private String categoryName;

	@ApiModelProperty("属性名称")
	private String name;

	@ApiModelProperty("属性英文名称")
	private String nameEnglish;

	@ApiModelProperty("标准号")
	private String standard;

	@ApiModelProperty("标准名称")
	private String standardName;

	@ApiModelProperty("技术要求")
	private String requirement;

	@ApiModelProperty("检测周期(工作日)")
	private Integer cycle;

	@ApiModelProperty("检测价格")
	private Long price;

	@ApiModelProperty("价格备注")
	private String desc;

	@ApiModelProperty("作为搜索参数 0:不需要，1:需要")
	private Integer searchType;

	@ApiModelProperty("检测资质0:cma，1:cnas，2：cma，cnas")
	private Integer attrType;

	private Long categoryIdTwo;

	private Long categoryIdOne;

	@ApiModelProperty("分类id")
	private Integer categoryNames[];
}
