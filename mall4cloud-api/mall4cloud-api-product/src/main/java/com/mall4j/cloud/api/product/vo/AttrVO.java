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
	private Long categoryName;

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


	public Long getAttrId() {
		return attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getAttrType() {
		return attrType;
	}

	public void setAttrType(Integer attrType) {
		this.attrType = attrType;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}


	@Override
	public String toString() {
		return "AttrVO{" +
				"attrId=" + attrId +
				", shopId=" + shopId +
				", name='" + name + '\'' +
				", desc='" + desc + '\'' +
				", attrType=" + attrType +
				", searchType=" + searchType +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
