package com.mall4j.cloud.product.controller.app;

import com.mall4j.cloud.api.multishop.bo.EsShopDetailBO;
import com.mall4j.cloud.api.multishop.feign.ShopDetailFeignClient;
import com.mall4j.cloud.api.multishop.vo.ShopAddrVO;
import com.mall4j.cloud.product.model.ShopAddr;
import com.mall4j.cloud.product.vo.app.*;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.product.service.SkuService;
import com.mall4j.cloud.product.service.SpuService;
import com.mall4j.cloud.api.product.vo.SpuVO;
import com.mall4j.cloud.product.vo.app.SkuAppVO;
import com.mall4j.cloud.product.vo.app.SpuAppVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * spu信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@RestController("appSpuController")
@RequestMapping("/ua/spu")
@Api(tags = "app-spu信息")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    ShopDetailFeignClient shopDetailFeignClient;
    @GetMapping("/prod_info")
    @ApiOperation(value = "服务方案详情信息", notes = "根据服务方案ID（prodId）获取商品信息")
    @ApiImplicitParam(name = "spuId", value = "服务方案ID", required = true, dataType = "Long")
    public ServerResponseEntity<SpuAppVO> prodInfo(@RequestParam("spuId") Long spuId){
        SpuVO spu = spuService.getBySpuId(spuId);
        SpuAppVO spuAppVO = mapperFacade.map(spu, SpuAppVO.class);
        List<SkuAppVO> skuAppVO = skuService.getSkuBySpuId(spu.getSpuId());
        ServerResponseEntity<ShopAddrVO> serverResponseEntity=shopDetailFeignClient.getAdderById(spu.getAddrId());
        ShopAddr shopAddr=mapperFacade.map(serverResponseEntity.getData(), ShopAddr.class);
        spuAppVO.setShopAddr(shopAddr);
        spuAppVO.setSkus(skuAppVO);
        return ServerResponseEntity.success(spuAppVO);
    }
}
