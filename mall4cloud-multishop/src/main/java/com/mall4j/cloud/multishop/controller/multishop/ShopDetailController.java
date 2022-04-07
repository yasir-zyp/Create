package com.mall4j.cloud.multishop.controller.multishop;

import com.mall4j.cloud.api.multishop.vo.ShopDetailVO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.multishop.dto.GatheringInfoDTO;
import com.mall4j.cloud.multishop.dto.ShopAddrDTO;
import com.mall4j.cloud.multishop.model.GatheringInfo;
import com.mall4j.cloud.multishop.service.ShopAddrService;
import com.mall4j.cloud.multishop.service.ShopDetailService;
import com.mall4j.cloud.multishop.vo.GatheringInfoVO;
import com.mall4j.cloud.multishop.vo.ShopAddrVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lth
 * @Date 2021/6/24 14:46
 */
@RequestMapping(value = "/m/shop_detail")
@RestController("multishopShopDetailController")
@Api(tags = "multishop-店铺详情信息")
public class ShopDetailController {

    @Autowired
    private ShopDetailService shopDetailService;
    @Autowired
    private ShopAddrService shopAddrService;
    @GetMapping("/info")
    @ApiOperation(value = "获取店铺详情信息", notes = "获取店铺详情信息")
    public ServerResponseEntity<ShopDetailVO> info() {
        Long shopId = AuthUserContext.get().getTenantId();
        ShopDetailVO shopDetailVO = shopDetailService.getByShopId(shopId);
        return ServerResponseEntity.success(shopDetailVO);
    }
    @PostMapping("/add_business_site")
    @ApiOperation(value = "添加机构地址信息", notes = "添加机构地址信息")
    public ServerResponseEntity<Object> AddBusinessAddress(@RequestBody ShopAddrDTO shopAddrDTO) {
        shopDetailService.creatBusinessAddress(shopAddrDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","添加成功");
        return ServerResponseEntity.success(map);
    }
    @GetMapping("/find_business_site")
    @ApiOperation(value = "查询机构地址信息", notes = "查询机构地址信息")
    public ServerResponseEntity<Object> FindBusinessAddress(@Valid PageDTO pageDTO) {
        PageVO<ShopAddrVO> shopAddrVOList=shopDetailService.findBusinessAddress(pageDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","查询成功");
        map.put("pages",shopAddrVOList.getPages());
        map.put("total",shopAddrVOList.getTotal());
        map.put("shopAddrVOList",shopAddrVOList.getList());
        return ServerResponseEntity.success(map);
    }
    @DeleteMapping("/del_business_site")
    @ApiOperation(value = "删除机构地址信息", notes = "删除机构地址信息")
    public ServerResponseEntity<Object> DelBusinessAddress(@RequestParam("shopAddrId") Long shopAddrId) {
        shopDetailService.delBusinessAddress(shopAddrId);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","删除成功");
        return ServerResponseEntity.success(map);
    }
    @PutMapping("/up_business_site")
    @ApiOperation(value = "修改机构地址信息", notes = "删除机构地址信息")
    public ServerResponseEntity<Object> UpdateBusinessAddress(@RequestBody ShopAddrDTO shopAddrDTO) {
        shopDetailService.upBusinessAddress(shopAddrDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","修改成功");
        return ServerResponseEntity.success(map);
    }
    @PostMapping("/ec_business_site")
    @ApiOperation(value = "回显机构地址信息", notes = "回显机构地址信息")
    public ServerResponseEntity<Object> EchoBusinessAddress(@RequestParam("shopAddrId") Long shopAddrId) {
        ShopAddrVO shopAddrVO=shopDetailService.echoBusinessAddress(shopAddrId);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","回显成功");
        map.put("shopAddrVO",shopAddrVO);
        return ServerResponseEntity.success(map);
    }

    @PostMapping("/add_account_info")
    @ApiOperation(value = "添加账户信息", notes = "添加账户信息")
    public ServerResponseEntity<Object> addAccountInformation(@RequestBody GatheringInfoDTO gatheringInfoDTO) {
        shopDetailService.creatAccountInfor(gatheringInfoDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","添加成功");
        return ServerResponseEntity.success(map);
    }
    @GetMapping("/find_account_info")
    @ApiOperation(value = "查询账户信息", notes = "查询账户信息")
    public ServerResponseEntity<Object> FindAccountInformation(@Valid PageDTO pageDTO) {
        PageVO<GatheringInfoVO> gatheringInfoVOList=shopDetailService.findAccountInfor(pageDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","查询成功");
        map.put("pages",gatheringInfoVOList.getPages());
        map.put("total",gatheringInfoVOList.getTotal());
        map.put("gatheringInfoVOList",gatheringInfoVOList.getList());
        return ServerResponseEntity.success(map);
    }
    @DeleteMapping("/del_account_info")
    @ApiOperation(value = "删除账户信息", notes = "删除账户信息")
    public ServerResponseEntity<Object> DelAccountInformation(@RequestParam("gatheringInfoId") Long gatheringInfoId) {
        shopDetailService.delAccountInfor(gatheringInfoId);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","删除成功");
        return ServerResponseEntity.success(map);
    }
    @PutMapping("/up_account_info")
    @ApiOperation(value = "修改账户信息", notes = "删除账户信息")
    public ServerResponseEntity<Object> UpdateAccountInformation(@RequestBody GatheringInfoDTO gatheringInfoDTO) {
        shopDetailService.upAccountInfor(gatheringInfoDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","修改成功");
        return ServerResponseEntity.success(map);
    }

    @PostMapping("/ec_account_info")
    @ApiOperation(value = "回显账户信息", notes = "回显账户信息")
    public ServerResponseEntity<Object> EchoAccountInformation(@RequestParam("gatheringInfoId") Long gatheringInfoId) {
        GatheringInfoVO gatheringInfoVO=shopDetailService.echoAccountInfor(gatheringInfoId);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","回显成功");
        map.put("gatheringInfoVO",gatheringInfoVO);
        return ServerResponseEntity.success(map);
    }
}
