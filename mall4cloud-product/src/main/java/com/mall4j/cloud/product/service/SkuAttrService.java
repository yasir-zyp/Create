package com.mall4j.cloud.product.service;

import com.mall4j.cloud.product.model.SkuAttr;

import java.util.List;

/**
 * 商品sku销售属性关联信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface SkuAttrService {

	/**
	 * 保存商品sku销售属性关联信息
	 *
	 * @param spuSkuAttrValue 商品sku销售属性关联信息
	 */
	void save(SkuAttr spuSkuAttrValue);

	/**
	 * 批量更新商品sku销售属性关联信息
	 *
	 * @param spuSkuAttrValues 商品sku销售属性关联信息
	 */
	void updateBatch(List<SkuAttr> spuSkuAttrValues);

	/**
	 * 根据商品sku销售属性关联信息id删除商品sku销售属性关联信息
	 *
	 * @param spuSkuAttrId
	 */
	void deleteById(Long spuSkuAttrId);

	/**
	 * 批量保存sku规格信息
	 *
	 * @param spuSkuAttrValues attrList
	 */
	void saveBatch(List<SkuAttr> spuSkuAttrValues);

	/**
	 * 根据spuId删除sku信息
	 *
	 * @param skuId skuId
	 */
	void updateBySkuId(Long skuId);

	/**
	 * 根据skuId列表，改变销售属性状态
	 * @param skuIds
	 * @param status
	 */
    void changeStatusBySkuId(List<Long> skuIds, Integer status);

    void delAll(Long skuId);

	void insertSelective(SkuAttr skuAttr);
}
