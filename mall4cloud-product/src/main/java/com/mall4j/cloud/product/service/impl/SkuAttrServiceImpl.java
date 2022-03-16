package com.mall4j.cloud.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mall4j.cloud.product.mapper.SkuAttrMapper;
import com.mall4j.cloud.product.model.SkuAttr;
import com.mall4j.cloud.product.service.SkuAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品sku销售属性关联信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Service
public class SkuAttrServiceImpl implements SkuAttrService {

    @Autowired
    private SkuAttrMapper skuAttrMapper;

    @Override
    public void save(SkuAttr spuSkuAttrValue) {
        skuAttrMapper.save(spuSkuAttrValue);
    }

    @Override
    public void updateBatch(List<SkuAttr> spuSkuAttrValues) {
        if (CollUtil.isNotEmpty(spuSkuAttrValues)) {
            skuAttrMapper.updateBatch(spuSkuAttrValues);
        }
    }

    @Override
    public void deleteById(Long spuSkuAttrId) {
        skuAttrMapper.deleteById(spuSkuAttrId);
    }

    @Override
    public void saveBatch(List<SkuAttr> spuSkuAttrValues) {
        if (CollUtil.isEmpty(spuSkuAttrValues)) {
            return;
        }
        skuAttrMapper.saveBatch(spuSkuAttrValues);
    }

    @Override
    public void updateBySkuId(Long skuId) {
        skuAttrMapper.updateBySkuId(skuId);
    }

    @Override
    public void changeStatusBySkuId(List<Long> skuIds, Integer status) {
        skuAttrMapper.changeStatusBySkuId(skuIds, status);
    }
}
