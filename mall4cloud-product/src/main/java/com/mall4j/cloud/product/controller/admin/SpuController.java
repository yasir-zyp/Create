package com.mall4j.cloud.product.controller.admin;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mall4j.cloud.common.constant.Constant;
import com.mall4j.cloud.common.constant.StatusEnum;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.product.dto.SkuDTO;
import com.mall4j.cloud.product.dto.SpuDTO;
import com.mall4j.cloud.product.dto.SpuIdDTO;
import com.mall4j.cloud.product.dto.SpuPageSearchDTO;
import com.mall4j.cloud.product.service.*;
import com.mall4j.cloud.api.product.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * spu信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@RestController("platformSpuController")
@RequestMapping("/admin/spu")
@Api(tags = "服务方案信息")
public class SpuController {

    @Autowired
    private SpuService spuService;
    @Autowired
    private SkuService skuService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrService attrService;
    @Autowired
    private SkuAttrService skuAttrService;

    @GetMapping("/platform_page")
    @ApiOperation(value = "获取平台spu信息列表", notes = "分页获取平台spu信息列表")
    public ServerResponseEntity<PageVO<SpuVO>> platformPage(PageDTO pageDTO, SpuPageSearchDTO spuDTO) {
        PageVO<SpuVO> spuPage = spuService.platformPage(pageDTO, spuDTO);
        return ServerResponseEntity.success(spuPage);
    }

    @GetMapping("/page")
    @ApiOperation(value = "获取服务方案列表", notes = "分页获取spu信息列表")
    public ServerResponseEntity<PageVO<SpuVO>> page(PageDTO pageDTO, SpuPageSearchDTO spuDTO) {
        PageVO<SpuVO> spuPage = spuService.page(pageDTO, spuDTO);
        return ServerResponseEntity.success(spuPage);
    }

    @GetMapping("/echo_spu")
    @ApiOperation(value = "回显服务方案", notes = "根据spuId获取spu信息")
    public ServerResponseEntity<SpuVO> getBySpuId(@RequestParam("spuId") Long spuId) {
        // 获取spu信息
        SpuVO spuVO = spuService.getBySpuId(spuId);
        // sku信息
        spuVO.setSkuList(skuService.listBySpuIdAndExtendInfo(spuId));
        //查询分类标签信息
        spuVO.setSpuCategoryName(spuService.SpuCategoryById(spuId));
        // 平台分类信息
        spuVO.setCategory(categoryService.getPathNameByCategoryId(spuVO.getCategoryId()));
        return ServerResponseEntity.success(spuVO);
    }


    @PostMapping("/add_spu")
    @ApiOperation(value = "保存服务方案", notes = "保存spu信息")
    public ServerResponseEntity<Object> save(@Valid @RequestBody SpuDTO spuDTO) {
        //checkSaveOrUpdateInfo(spuDTO);
       spuService.save(spuDTO);
        return ServerResponseEntity.success();
    }

    @PutMapping("/up_spu")
    @ApiOperation(value = "更新服务方案", notes = "更新spu信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody SpuDTO spuDTO) {
        //checkSaveOrUpdateInfo(spuDTO);
        List<Long> skuIds = spuDTO.getSkuList().stream().filter(sku -> Objects.nonNull(sku.getSkuId())).map(SkuDTO::getSkuId).collect(Collectors.toList());
        spuService.update(spuDTO);
        // 清除缓存
        spuService.removeSpuCacheBySpuId(spuDTO.getSpuId());
        skuService.removeSkuCacheBySpuIdOrSkuIds(spuDTO.getSpuId(), skuIds);
        return ServerResponseEntity.success();
    }


    @DeleteMapping("/del_spu")
    @ApiOperation(value = "删除服务方案", notes = "根据spu信息id删除spu信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long spuId) {
        spuService.deleteById(spuId);
        // 清除缓存
        spuService.removeSpuCacheBySpuId(spuId);
        skuService.removeSkuCacheBySpuIdOrSkuIds(spuId, null);
        return ServerResponseEntity.success();
    }

/*    @PutMapping("/update_spu_data")
    @ApiOperation(value = "修改服务方案", notes = "更新spu信息")
    public ServerResponseEntity<Void> updateSpuData(@RequestBody SpuDTO spuDTO) {
        spuService.updateSpuOrSku(spuDTO);
        // 清除缓存
        spuService.removeSpuCacheBySpuId(spuDTO.getSpuId());
        skuService.removeSkuCacheBySpuIdOrSkuIds(spuDTO.getSpuId(), null);
        return ServerResponseEntity.success();
    }*/


    /**
     * 更新商品状态
     */
    @PutMapping("/prod_status")
    @ApiOperation(value = "发布(撤回)服务方案", notes = "发布(撤回)服务方案")
    public ServerResponseEntity<Object> spuChangeStatus(@RequestBody SpuPageSearchDTO spuPageSearchDTO) {
        if (Objects.nonNull(spuPageSearchDTO.getSpuId())) {
            spuUpdateStatus(spuPageSearchDTO);
        } else if (CollUtil.isNotEmpty(spuPageSearchDTO.getSpuIds())) {
            spuBatchUpdateStatus(spuPageSearchDTO);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("msg","发布成功");
        return ServerResponseEntity.success(map);
    }

    /**
     * spu上下架
     * @param spu
     */
    private void spuUpdateStatus(SpuPageSearchDTO spu) {
        SpuVO dbSpu = spuService.getBySpuId(spu.getSpuId());
        String error = checkUpdateStatusData(dbSpu);
        if (StrUtil.isNotBlank(error)) {
            throw new Mall4cloudException(error);
        }
        spuService.changeSpuStatus(spu.getSpuId(), spu.getStatus());
        spuService.removeSpuCacheBySpuId(spu.getSpuId());
    }

    /**
     * spu批量上下架
     * @param spu
     */
    private void spuBatchUpdateStatus(SpuPageSearchDTO spu) {
        List<Long> spuIds = new ArrayList<>(spu.getSpuIds());
        List<Long> errorList = new ArrayList<>();
        List<SpuVO> spuList = spuService.listBySpuIds(spu.getSpuIds(), null, null);
        if (CollUtil.isEmpty(spuList)) {
            throw new Mall4cloudException("您选择的商品信息有误，请刷新后重试");
        }
        Map<Long, SpuVO> spuMap = spuList.stream().collect(Collectors.toMap(SpuVO::getSpuId, s -> s));
        for (Long spuId : spu.getSpuIds()) {
            String errorInfo = checkUpdateStatusData(spuMap.get(spuId));
            if (StrUtil.isNotBlank(errorInfo)) {
                spuIds.remove(spuId);
                errorList.add(spuId);
            }
        }
        if (CollUtil.isEmpty(spuIds)) {
            throw new Mall4cloudException("您所选择的商品中没有符合操作条件的商品");
        }
        spuService.batchRemoveSpuCacheBySpuId(spuIds);
        if (errorList.size() > 0) {
            throw new Mall4cloudException("商品id为：" + errorList.toString() + "的" + errorList.size() + "件商品不符合操作条件");
        }
        spuService.changeSpuIdListStatus(spu.getSpuIds(), spu.getStatus());
    }



    /**
     * 校验spu新增或更新信息
     * @param spuDTO
     */
    /*private void checkSaveOrUpdateInfo(SpuDTO spuDTO) {
        if (!Objects.equals(Constant.PLATFORM_SHOP_ID, AuthUserContext.get().getTenantId()) && Objects.isNull(spuDTO.getShopCategoryId())) {
            throw new Mall4cloudException("店铺分类不能为空");
        }
        if (Objects.isNull(spuDTO.getSeq())) {
            spuDTO.setSeq(0);
        }
        if (Objects.isNull(spuDTO.getBrandId())) {
            spuDTO.setBrandId(0L);
        }
    }*/

    /**
     * 校验spu上下架信息
     * @param spu
     * @return
     */
    private String checkUpdateStatusData(SpuVO spu) {
        //Long shopId = AuthUserContext.get().getTenantId();
        if (Objects.isNull(spu)) {
            return "查找不到该商品信息";
        }
        if (!Objects.equals(spu.getShopId(), AuthUserContext.get().getTenantId())){
            return "查找不到该商品信息";
        }
        if (!(Objects.equals(spu.getStatus(), StatusEnum.ENABLE.value())
                || Objects.equals(spu.getStatus(), StatusEnum.DISABLE.value()))) {
            return "商品状态异常，清刷新后重试";
        }
       /* if(Objects.equals(spu.getStatus(),StatusEnum.ENABLE.value())){
            CategoryVO category = categoryService.getById(spu.getCategoryId());
            if (Objects.equals(category.getStatus(), StatusEnum.DISABLE.value())){
                return "该商品所属的平台分类处于下线中，商品不能上架，请联系管理员后再进行操作";            }

            if (Objects.equals(Constant.PLATFORM_SHOP_ID, AuthUserContext.get().getTenantId())) {
                CategoryVO shopCategory = categoryService.getById(spu.getShopCategoryId());
                if (Objects.equals(category.getStatus(), StatusEnum.DISABLE.value())){
                    return "该商品所属的店铺分类禁用中，商品不能进行上架操作";
                }
            }
        }*/
        return null;
    }
    /*单个删除skulist信息*/
    @DeleteMapping("/del_sku_some")
    @ApiOperation(value = "删除sku信息", notes = "根据sku信息")
    public ServerResponseEntity<Void> delSku(@RequestParam Long skuId) {
        skuService.deleteById(skuId);
        skuAttrService.delAll(skuId);
        return ServerResponseEntity.success();
    }
    /*单个删除skuAttrs信息*/
    @DeleteMapping("/del_sku_attr")
    @ApiOperation(value = "删除skuAttrList信息", notes = "根据sku信息")
    public ServerResponseEntity<Void> delSkuAttr(@RequestParam Long skuAttrId) {
        skuAttrService.deleteById(skuAttrId);
        return ServerResponseEntity.success();
    }
}
