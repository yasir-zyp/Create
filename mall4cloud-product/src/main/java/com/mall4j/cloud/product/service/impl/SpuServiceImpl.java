package com.mall4j.cloud.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.api.multishop.feign.IndexImgFeignClient;
import com.mall4j.cloud.api.multishop.feign.ShopDetailFeignClient;
import com.mall4j.cloud.api.multishop.vo.ShopDetailVO;
import com.mall4j.cloud.api.product.bo.EsAttrBO;
import com.mall4j.cloud.api.product.bo.EsProductBO;
import com.mall4j.cloud.api.product.vo.*;
import com.mall4j.cloud.common.cache.constant.CacheNames;
import com.mall4j.cloud.common.cache.util.RedisUtil;
import com.mall4j.cloud.common.constant.Constant;
import com.mall4j.cloud.common.constant.StatusEnum;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.util.PageUtil;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.product.dto.SpuDTO;
import com.mall4j.cloud.product.dto.SpuPageSearchDTO;
import com.mall4j.cloud.product.mapper.SkuMapper;
import com.mall4j.cloud.product.mapper.SpuCategoryMapper;
import com.mall4j.cloud.product.mapper.SpuMapper;
import com.mall4j.cloud.product.model.Sku;
import com.mall4j.cloud.product.model.SpuCategory;
import com.mall4j.cloud.product.service.*;
import com.mall4j.cloud.product.model.Spu;
import com.mall4j.cloud.product.model.SpuDetail;

import com.mall4j.cloud.product.vo.app.SkuAppVO;
import io.seata.spring.annotation.GlobalTransactional;
import ma.glasnost.orika.MapperFacade;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * spu信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailService spuDetailService;


    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private SkuService skuService;


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ShopDetailFeignClient shopDetailFeignClient;

    @Autowired
    private IndexImgFeignClient indexImgFeignClient;

    @Autowired
    private SpuCategoryMapper spuCategoryMapper;
   @Autowired
    SkuMapper skuMapper;
    @Override
    public PageVO<SpuVO> page(PageDTO pageDTO, SpuPageSearchDTO spuDTO) {
        spuDTO.setShopId(AuthUserContext.get().getTenantId());
        PageVO<SpuVO> spuPage = PageUtil.doPage(pageDTO, () -> spuMapper.list(spuDTO));
        return spuPage;
    }

    @Override
    @Cacheable(cacheNames = CacheNames.SPU_KEY, key = "#spuId",sync = true)
    public SpuVO getBySpuId(Long spuId) {
        return spuMapper.getBySpuId(spuId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheNames.SPU_KEY, key = "#spuId"),
            @CacheEvict(cacheNames = CacheNames.SPU_EXTENSION_KEY, key = "#spuId")
    })
    public void removeSpuCacheBySpuId(Long spuId) {
    }

    @Override
    public void batchRemoveSpuCacheBySpuId(List<Long> spuIds) {
        RedisUtil.deleteBatch(CacheNames.SPU_KEY, spuIds);
        RedisUtil.deleteBatch(CacheNames.SPU_EXTENSION_KEY, spuIds);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public void changeSpuStatus(Long spuId, Integer status) {
        spuMapper.changeSpuStatus(spuId, status);
       /* if (!Objects.equals(status, StatusEnum.ENABLE)) {
            SpuVO spuVO = spuMapper.getBySpuId(spuId);
            ServerResponseEntity<Void> imgRes = indexImgFeignClient.deleteBySpuId(spuVO.getSpuId(), spuVO.getShopId());
            if (!imgRes.isSuccess()) {
                throw new Mall4cloudException("服务异常");
            }
        }*/
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SpuDTO spuDTO) {
        Spu spu = mapperFacade.map(spuDTO, Spu.class);
        spu.setShopId(AuthUserContext.get().getTenantId());
        spu.setStatus(StatusEnum.DISABLE.value());
        spu.setAddrId(spu.getShopAddr().getShopAddrId());
        // 1.保存商品信息
        spuMapper.save(spu);
        // 2.保存商品其他信息、详细、扩展信息
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spu.getSpuId());
        spuDetail.setDetail(spuDTO.getDetail());
        spuDetailService.save(spuDetail);
        if (spuDTO.getCategoryIds()!=null){
            for (int i = 0; i <spuDTO.getCategoryIds().size() ; i++) {
                SpuCategory spuCategory=new SpuCategory();
                spuCategory.setCategoryId(spuDTO.getCategoryIds().get(i));
                spuCategory.setShopId(AuthUserContext.get().getTenantId());
                spuCategory.setSpuId(spu.getSpuId());
                spuCategoryMapper.insertSelective(spuCategory);
            }
        }
        // 3.保存sku信息
        skuService.save(spu.getSpuId(),spuDTO.getSkuList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SpuDTO spuDTO) {
        Spu spu = mapperFacade.map(spuDTO, Spu.class);
        SpuServiceImpl spuService = (SpuServiceImpl) AopContext.currentProxy();
        SpuVO dbSpu = spuService.getBySpuId(spu.getSpuId());
        spu.setAddrId(spu.getShopAddr().getShopAddrId());
        // 1.修改商品信息
        spuMapper.update(spu);
        // 2.修改商品详情
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spu.getSpuId());
        spuDetail.setDetail(spuDTO.getDetail());
        spuDetailService.update(spuDetail);


        // 4.修改商品sku信息
        skuService.update(spu.getSpuId(),spuDTO.getSkuList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional(rollbackFor = Exception.class)
    public void deleteById(Long spuId) {
        SpuVO spuVO = getBySpuId(spuId);
        if(Objects.isNull(spuVO) || Objects.equals(spuVO.getStatus(), StatusEnum.DELETE.value())){
            throw new Mall4cloudException("商品不存在或者已被删除！");
        }
        // 删除商品、sku信息(逻辑删除)
        spuMapper.updateStatusBySpuId(spuId);
        skuService.deleteBySpuId(spuId);
        // 把轮播图中关联了该商品的数据删除
        ServerResponseEntity<Void> imgRes = indexImgFeignClient.deleteBySpuId(spuId, spuVO.getShopId());
        if (!imgRes.isSuccess()) {
            throw new Mall4cloudException("服务异常");
        }
    }

    @Override
    public void updateSpuOrSku(SpuDTO spuDTO) {
        Spu spu = new Spu();
        spu.setSpuId(spuDTO.getSpuId());
        spu.setName(spuDTO.getName());
        spu.setSeq(spuDTO.getSeq());
        if (CollUtil.isNotEmpty(spuDTO.getSkuList())) {
            skuService.updateAmount(spuDTO);
        }
        spu.setPriceFee(spuDTO.getPriceFee());
        spuMapper.update(spu);
    }

    @Override
    public void updateSpuUpdateTime(List<Long> spuIds, List<Long> categoryIds) {
        if (CollUtil.isEmpty(spuIds) && CollUtil.isEmpty(categoryIds)) {
            return;
        }
        spuMapper.updateSpuUpdateTime(spuIds, categoryIds);
    }

    @Override
    public EsProductBO loadEsProductBO(Long spuId) {
        // 获取服务方案数据
        EsProductBO esProductBO = spuMapper.loadEsProductBO(spuId);
        // 获取分类数据
        CategoryVO category = categoryService.getPathNameByCategoryId(esProductBO.getCategoryId());
        List<SkuAppVO> skuVOList=skuService.getSkuBySpuId(spuId);
        String[] categoryIdArray = category.getPath().split(Constant.CATEGORY_INTERVAL);
        List<SkuAttrVO> skuAttrVOList=new ArrayList<>();
        for(SkuAppVO skuAppVO:skuVOList){
            skuAttrVOList.addAll(skuAppVO.getSkuAttrs());
        }
        List<EsAttrBO> esAttrBOList=new ArrayList<>();
        for (SkuAttrVO attr:skuAttrVOList
             ) {
            esAttrBOList.add(mapperFacade.map(attr,EsAttrBO.class));
        }
        esProductBO.setAttrs(esAttrBOList);
        esProductBO.setCategoryName(category.getName());
        for (int i = 0;i<categoryIdArray.length;i++) {
            if (i == 0) {
                esProductBO.setPrimaryCategoryId(Long.valueOf(categoryIdArray[i]));
                esProductBO.setPrimaryCategoryName(category.getPathNames().get(i));
            } else {
                esProductBO.setSecondaryCategoryId(Long.valueOf(categoryIdArray[i]));
                esProductBO.setSecondaryCategoryName(category.getPathNames().get(i));
            }
        }
        return esProductBO;
    }

    @Override
    public List<Long> getSpuIdsBySpuUpdateDTO( List<Long> categoryIds,  Long shopId) {
        if (CollUtil.isEmpty(categoryIds)  && Objects.isNull(shopId)) {
            return new ArrayList<>();
        }
        return spuMapper.getSpuIdsBySpuUpdateDTO(categoryIds,  shopId);
    }

    @Override
    public List<SpuVO> listBySpuIds(List<Long> spuIds, String prodName, Integer isFailure) {
        if (CollUtil.isEmpty(spuIds)) {
            return new ArrayList<>();
        }
        return spuMapper.listBySpuIds(spuIds,prodName,isFailure);
    }

    @Override
    public PageVO<SpuVO> platformPage(PageDTO pageDTO, SpuPageSearchDTO spuDTO) {
        PageVO<SpuVO> page = page(pageDTO, spuDTO);
        Set<Long> shopIdSet = page.getList().stream().map(SpuVO::getShopId).collect(Collectors.toSet());
        ServerResponseEntity<List<ShopDetailVO>> shopResponse = shopDetailFeignClient.listByShopIds(new ArrayList<>(shopIdSet));
        Map<Long, ShopDetailVO> shopMap = shopResponse.getData().stream().collect(Collectors.toMap(ShopDetailVO::getShopId, s -> s));

        for (SpuVO spuVO : page.getList()) {
            ShopDetailVO shopDetailVO = shopMap.get(spuVO.getShopId());
            if (Objects.isNull(shopDetailVO)) {
                continue;
            }
            spuVO.setShopName(shopDetailVO.getShopName());
        }
        return page;
    }

    @Override
    public SpuVO getSpuDetailData(Long spuId) {
        return null;
    }

    @Override
    public PageVO<SpuVO> pageByLangAndTagId(PageDTO pageDTO, SpuDTO spuDTO, Integer isContain) {
        return PageUtil.doPage(pageDTO, () -> spuMapper.pageByLangAndTagId(pageDTO,spuDTO,isContain));
    }

    @Override
    public void batchChangeSpuStatusByCids(List<Long> cidList, Long shopId, Integer status) {
        List<Long> spuIdList;
        if (Objects.equals(shopId, Constant.PLATFORM_SHOP_ID)) {
            spuIdList = spuMapper.listIdsByCidListAndTypeAndShopId(cidList, 1, shopId);
        } else {
            spuIdList = spuMapper.listIdsByCidListAndTypeAndShopId(cidList, 2, shopId);
        }
        if (Objects.isNull(spuIdList) || spuIdList.size() == 0) {
            return;
        }
        spuMapper.batchChangeSpuStatusBySpuIdsAndStatus(spuIdList, status);
        this.batchRemoveSpuCacheBySpuId(spuIdList);
    }

    @Override
    public List<String> SpuCategoryById(Long spuId) {
        return spuCategoryMapper.SpuCategoryById(spuId);
    }

    @Override
    public void changeSpuIdListStatus(List<Long> spuIds, Integer status) {
     spuMapper.batchChangeSpuStatusBySpuIdsAndStatus(spuIds,status);
    }
}
