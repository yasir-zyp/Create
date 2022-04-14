package com.mall4j.cloud.product.model;

import com.mall4j.cloud.common.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品sku销售属性关联信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Data
public class SkuAttr extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 商品sku销售属性关联信息id
     */
    private Long skuAttrId;

    /**
     * SPU ID
     */
    private Long spuId;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 销售属性ID
     */
    private Long attrId;

    /**
     * 销售属性名称
     */
    private String attrName;

    private Long priceFee;


    private String attrDesc;
    /**
     * 状态 1:enable, 0:disable, -1:deleted
     */
    private Integer status;
}
