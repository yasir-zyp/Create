package com.mall4j.cloud.product.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mall4j.cloud.common.model.BaseModel;
import lombok.Data;

/**
 * sku信息
 *
 * @author FrozenWatermelon
 * @date 2020-12-08 15:54:22
 */
@Data
public class Sku extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 属性id
     */
    private Long skuId;

    /**
     * SPU id
     */
    private Long spuId;

    /**
     * 多个销售属性值id逗号分隔
     */
    private String attrs;
	/**
	 * 积分数
	 */
	private Integer integral;
    /**
     * sku简介
     */
    private String skuName;



    /**
     * 售价，整数方式保存
     */
    private Long priceFee;

    /**
     * 市场价，整数方式保存
     */
    private Long marketPriceFee;




    /**
     * 商品重量
     */
    private BigDecimal weight;

    /**
     * 商品体积
     */
    private BigDecimal volume;

    /**
     * 状态 1:enable, 0:disable, -1:deleted
     */
    private Integer status;

}
