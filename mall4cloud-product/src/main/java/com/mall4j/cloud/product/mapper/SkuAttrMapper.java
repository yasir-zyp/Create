package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.product.model.SkuAttr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品sku销售属性关联信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
public interface SkuAttrMapper {

	/**
	 * 保存商品sku与检测项目关联信息
	 *
	 * @param skuAttr 商品sku销售属性关联信息
	 */
	void save(@Param("skuAttr") SkuAttr skuAttr);

	/**
	 * 更新商品sku销售属性关联信息
	 *
	 * @param skuAttr 服务方案sku与检测项关联信息
	 */
	void update(@Param("skuAttr") SkuAttr skuAttr);

	/**
	 * 批量更新商品sku销售属性关联信息
	 *
	 * @param skuAttrs 商品sku销售属性关联信息
	 */
	void updateBatch(@Param("skuAttrs") List<SkuAttr> skuAttrs);

	/**
	 * 根据商品sku销售属性关联信息id删除商品sku销售属性关联信息
	 *
	 * @param skuAttrId
	 */
	void deleteById(@Param("skuAttrId") Long skuAttrId);

	/**
	 * 批量保存sku规格信息
	 *
	 * @param skuAttrs attrList
	 */
	void saveBatch(@Param("skuAttrs") List<SkuAttr> skuAttrs);

	/**
	 * 修改商品规格信息
	 *
	 * @param skuId
	 */
	void updateBySkuId(@Param("skuId") Long skuId);

	/**
	 * 根据skuId列表，改变销售属性状态
	 * @param skuIds
	 * @param status
	 */
    void changeStatusBySkuId(@Param("skuIds") List<Long> skuIds, @Param("status") Integer status);
}
