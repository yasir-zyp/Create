package com.mall4j.cloud.product.feign;

import com.mall4j.cloud.api.multishop.bo.EsShopDetailBO;
import com.mall4j.cloud.api.multishop.feign.ShopDetailFeignClient;
import com.mall4j.cloud.api.product.bo.EsAttrBO;
import com.mall4j.cloud.api.product.bo.EsProductBO;
import com.mall4j.cloud.api.product.feign.ProductFeignClient;
import com.mall4j.cloud.api.product.feign.SkuFeignClient;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.product.service.AttrService;
import com.mall4j.cloud.product.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author FrozenWatermelon
 * @date 2020/11/27
 */
@RestController
public class ProductFeignController implements ProductFeignClient {

    @Autowired
    private SpuService spuService;
    @Autowired
    private ShopDetailFeignClient shopDetailFeignClient;
    @Autowired
    private AttrService attrService;


    @Override
    public ServerResponseEntity<EsProductBO> loadEsProductBO(Long spuId) {
        EsProductBO esProductBO = spuService.loadEsProductBO(spuId);
        // 获取店铺信息
        ServerResponseEntity<EsShopDetailBO> shopDetailResponse = shopDetailFeignClient.getShopByShopId(esProductBO.getShopId());
        EsShopDetailBO shopDetail = shopDetailResponse.getData();
        esProductBO.setShopName(shopDetail.getShopName());
        esProductBO.setShopImg(shopDetail.getShopLogo());
        esProductBO.setShopType(shopDetail.getType());
        if (Objects.isNull(esProductBO.getSaleNum())) {
            esProductBO.setSaleNum(0);
        }
        return ServerResponseEntity.success(esProductBO);
    }



    @Override
    public ServerResponseEntity<List<Long>> getSpuIdsByCategoryIds(List<Long> categoryIds) {
        return getSpuIdsBySpuUpdateDTO( categoryIds,  null);
    }


    @Override
    public ServerResponseEntity<List<Long>> getSpuIdsByShopId(Long shopId) {
        return getSpuIdsBySpuUpdateDTO(null,  shopId);
    }

    /**
     * 获取spuId列表
     * @param categoryIds 平台分类Id列表
     * @param shopId 店铺id
     * @return
     */
    public ServerResponseEntity<List<Long>> getSpuIdsBySpuUpdateDTO( List<Long> categoryIds,  Long shopId) {
        List<Long> spuIds = spuService.getSpuIdsBySpuUpdateDTO( categoryIds, shopId);
        return ServerResponseEntity.success(spuIds);
    }
}
