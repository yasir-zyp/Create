package com.mall4j.cloud.product.model;

import java.io.Serializable;

import com.mall4j.cloud.common.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 属性信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:23
 */
@Data
public class Attr extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;


	/**
     * attr id
     */
    private Long attrId;

	/**
	 * 店铺id
	 */
	private Long shopId;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性描述
     */
    private String desc;

	/**
	 * 作为搜索参数 0:不需要，1:需要
	 */
	private Integer searchType;

	/**
	 * 属性类型 0:销售属性，1:基本属性
	 */
	private Integer attrType;

	/**
	 * 分类id
	 */
	private Long categoryId;
	/**
	 * 分类名称
	 */
	private String categoryName;

	/**
	 * 属性英文名称
	 */
	private String nameEnglish;
	/**
	 * 标准号
	 */
	private String standard;
	/**
	 * 标准名称
	 */
	private String standardName;
	/**
	 * 技术要求
	 */
	private String requirement;
	/**
	 * 检测周期(工作日)
	 */
	private Integer cycle;
	/**
	 * 检测价格
	 */
	private Long price;

	private Long categoryIdTwo;

	private Long categoryIdOne;

	private Integer categoryNames[];
}
