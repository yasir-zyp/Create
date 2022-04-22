package com.mall4j.cloud.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mall4j.cloud.api.product.vo.AttrVO;
import com.mall4j.cloud.api.product.vo.SkuAttrVO;
import com.mall4j.cloud.common.cache.constant.CacheNames;
import com.mall4j.cloud.common.cache.util.RedisUtil;
import com.mall4j.cloud.common.constant.StatusEnum;
import com.mall4j.cloud.product.dto.SkuAttrDTO;
import com.mall4j.cloud.product.dto.SkuDTO;
import com.mall4j.cloud.product.dto.SpuDTO;
import com.mall4j.cloud.product.mapper.AttrMapper;
import com.mall4j.cloud.product.mapper.SkuAttrMapper;
import com.mall4j.cloud.product.mapper.SkuMapper;
import com.mall4j.cloud.product.model.Attr;
import com.mall4j.cloud.product.model.Sku;
import com.mall4j.cloud.product.model.SkuAttr;
import com.mall4j.cloud.product.service.SkuService;
import com.mall4j.cloud.product.service.SkuAttrService;
import com.mall4j.cloud.api.product.vo.SkuVO;
import com.mall4j.cloud.product.vo.app.SkuAppVO;
import ma.glasnost.orika.MapperFacade;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * sku信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SkuAttrService skuAttrService;
    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private AttrMapper attrMapper;
    @Autowired
    private SkuAttrMapper skuAttrMapper;
    @Override
    public void save(Long spuId, List<SkuDTO> skuList) {
        skuList.forEach(sku -> {
            sku.setSpuId(spuId);
            sku.setStatus(StatusEnum.ENABLE.value());
        });
        // 处理数据，保存库存、属性
        skuMapper.saveBatch(skuList);
        //List<SkuAttr> skuAttrs = new ArrayList<>();
      for (SkuDTO skuDTO : skuList) {
          List<SkuAttr> skuAttrList = mapperFacade.mapAsList(skuDTO.getSkuAttrs(), SkuAttr.class);
          for (int i = 0; i <skuAttrList.size() ; i++) {
              SkuAttr skuAttr=mapperFacade.map(skuDTO.getSkuAttrs().get(i), SkuAttr.class);
              skuAttr.setSpuId(spuId);
              skuAttr.setSkuId(skuDTO.getSkuId());
              skuAttr.setStatus(StatusEnum.ENABLE.value());
              skuAttr.setPriceFee(skuDTO.getSkuAttrs().get(i).getPrice());
              skuAttrService.insertSelective(skuAttr);
          }
        }
        /*   for (SkuAttr skuAttr : skuAttrList) {
                skuAttr.setSpuId(spuId);
                skuAttr.setSkuId(skuDTO.getSkuId());
                skuAttr.setStatus(StatusEnum.ENABLE.value());
                skuAttrs.add(skuAttr);
                skuAttrService.insertSelective(skuAttrs);
            }*/
    }


    @Override
    public void update(Long spuId, List<SkuDTO> skuList) {
        // 获取当前商品所有的sku
        List<SkuVO> skuListDb = skuMapper.listBySpuId(spuId);
        List<Long> skuIdsDb = skuListDb.stream().map(SkuVO::getSkuId).collect(Collectors.toList());
        List<Long> skuIds = new ArrayList<>();
        List<SkuDTO> updateSkus = new ArrayList<>();
        List<SkuDTO> insertSkus = new ArrayList<>();
        for (SkuDTO sku : skuList) {
            sku.setSpuId(spuId);
            if (skuIdsDb.contains(sku.getSkuId())) {
                updateSkus.add(sku);
                skuIds.add(sku.getSkuId());
            } else if (Objects.isNull(sku.getSkuId())){
                insertSkus.add(sku);
            }
        }
        // 新增的sku--保存
        if(CollUtil.isNotEmpty(insertSkus)) {
            save(spuId,insertSkus);
        }
        // 已有的sku--更新
        if(CollUtil.isNotEmpty(updateSkus)){
            List<Sku> skus = mapperFacade.mapAsList(updateSkus, Sku.class);
            for (SkuDTO sku : skuList
                 ) {
                List<SkuAttrDTO> skuAttrDTOS=sku.getSkuAttrs();
                for (int i = 0; i <skuAttrDTOS.size() ; i++) {
                    if (skuAttrDTOS.get(i).getSpuSkuAttrId()!=null){
                        SkuAttr skuAttr=new SkuAttr();
                        skuAttr.setPriceFee(skuAttrDTOS.get(i).getPrice());
                        skuAttr.setSkuAttrId(skuAttrDTOS.get(i).getSpuSkuAttrId());
                        skuAttrMapper.update(skuAttr);
                    }else {
                        SkuAttr skuAttr=new SkuAttr();
                        skuAttr.setSpuId(spuId);
                        skuAttr.setSkuId(sku.getSkuId());
                        skuAttr.setStatus(StatusEnum.ENABLE.value());
                        skuAttr.setPriceFee(skuAttrDTOS.get(i).getSpuSkuAttrId());
                        skuAttrService.insertSelective(skuAttr);
                    }
                }
            }
            skuMapper.updateBatch(skus);
        }
        // 不存在的sku--删除
        skuIdsDb.removeAll(skuIds);
        if (CollUtil.isNotEmpty(skuIdsDb)) {
            deleteSkuBatch(skuIdsDb);
        }
    }

    @Override
    public void deleteById(Long skuId) {
        skuMapper.deleteById(skuId);
    }

    @Override
    @Cacheable(cacheNames = CacheNames.SKU_LIST_KEY, key = "#spuId",sync = true)
    public List<SkuVO> listBySpuId(Long spuId) {
        return skuMapper.listBySpuId(spuId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheNames.SKU_LIST_KEY, key = "#spuId"),
            @CacheEvict(cacheNames = CacheNames.SKU_OF_SPU_DETAIL_KEY, key = "#spuId")
    })
    public void removeSkuCacheBySpuIdOrSkuIds(Long spuId, List<Long> skuIds) {
        // 根据spuId删除缓存
        if (CollUtil.isEmpty(skuIds)) {
            // 获取当前类的代理，这样就可以利用spring的方法获取缓存了，不要删了
            SkuServiceImpl skuService = (SkuServiceImpl) AopContext.currentProxy();
            List<SkuVO> skuList =  skuService.listBySpuId(spuId);
            skuIds = skuList.stream().map(SkuVO::getSkuId).collect(Collectors.toList());
        }
        RedisUtil.deleteBatch(CacheNames.SKU_BY_ID_KEY, skuIds);
    }

    @Override
    public void deleteBySpuId(Long spuId) {
        skuMapper.updateBySpuId(spuId);
    }

    @Override
    public List<SkuVO> listBySpuIdAndExtendInfo(Long spuId) {
        List<SkuVO> skuVOLis=skuMapper.listBySpuIdAndExtendInfo(spuId);
        for (SkuVO skuVo:skuVOLis
             ) {
            List<SkuAttrVO> skuAttrVOS=skuVo.getSkuAttrs();
            for (int i = 0; i <skuAttrVOS.size() ; i++) {
                AttrVO attrVO=attrMapper.getByAttrId(skuAttrVOS.get(i).getAttrId());
                skuAttrVOS.get(i).setStandardName(attrVO.getStandardName());
                skuAttrVOS.get(i).setCycle(attrVO.getCycle());
                skuAttrVOS.get(i).setPrice(attrVO.getPrice());
            }
        }
        return skuVOLis;
    }

    @Override
    @Cacheable(cacheNames = CacheNames.SKU_BY_ID_KEY, key = "#skuId")
    public SkuVO getSkuBySkuId(Long skuId) {
        return skuMapper.getSkuBySkuId(skuId);
    }

    @Override
    public void updateAmount(SpuDTO spuDTO) {
        List<SkuDTO> skuList = spuDTO.getSkuList();
        List<Sku> skus = new ArrayList<>();
        for (SkuDTO skuDTO : skuList) {
            if (Objects.nonNull(skuDTO.getPriceFee())) {
                Sku sku = new Sku();
                sku.setSkuId(skuDTO.getSkuId());
                sku.setPriceFee(skuDTO.getPriceFee());
                skus.add(sku);
            }
        }
        if (CollUtil.isNotEmpty(skus)) {
            skuMapper.updateBatch(skus);
        }
    }

    @Override
    @Cacheable(cacheNames = CacheNames.SKU_OF_SPU_DETAIL_KEY, key = "#spuId",sync = true)
    public List<SkuAppVO> getSkuBySpuId(Long spuId) {
        String attrUnionAttr = ";";
        List<SkuAppVO> skuAppList = new ArrayList<>();
        List<SkuVO> skuData = skuMapper.getSkuBySpuId(spuId);
        for (SkuVO sku : skuData) {
            SkuAppVO skuAppVO = mapperFacade.map(sku, SkuAppVO.class);
            String properties = "";
            for (SkuAttrVO skuAttr : sku.getSkuAttrs()) {
                properties = properties + skuAttr.getAttrName()  + attrUnionAttr;
            }
            skuAppVO.setProperties(properties.substring(0, properties.length()-1));
            skuAppList.add(skuAppVO);
        }
        return skuAppList;
    }

    private void deleteSkuBatch(List<Long> skuIds) {
        List<Sku> skus = new ArrayList<>();
        for (Long skuId : skuIds) {
            Sku sku = new Sku();
            sku.setSkuId(skuId);
            sku.setStatus(StatusEnum.DELETE.value());
            skus.add(sku);
        }
        skuMapper.updateBatch(skus);
        skuAttrService.changeStatusBySkuId(skuIds, StatusEnum.DELETE.value());
    }
}
