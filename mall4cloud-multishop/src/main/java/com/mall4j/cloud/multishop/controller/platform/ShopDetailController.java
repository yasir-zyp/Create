package com.mall4j.cloud.multishop.controller.platform;

import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.api.auth.dto.AuthAccountDTO;
import com.mall4j.cloud.api.auth.feign.AccountFeignClient;
import com.mall4j.cloud.api.auth.vo.TokenInfoVO;
import com.mall4j.cloud.api.multishop.vo.ShopDetailVO;
import com.mall4j.cloud.common.constant.Constant;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.common.security.bo.AuthAccountInVerifyBO;
import com.mall4j.cloud.common.security.constant.InputUserNameEnum;
import com.mall4j.cloud.multishop.dto.AuthDTO;
import com.mall4j.cloud.multishop.dto.BasicInformationDTO;
import com.mall4j.cloud.multishop.dto.ShopAptitudeDTO;
import com.mall4j.cloud.multishop.dto.ShopDetailDTO;
import com.mall4j.cloud.multishop.manager.TokenStore;
import com.mall4j.cloud.multishop.model.ShopDetail;
import com.mall4j.cloud.multishop.model.ShopUser;
import com.mall4j.cloud.multishop.service.ShopDetailService;
import com.mall4j.cloud.multishop.service.ShopUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 店铺详情
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 15:50:25
 */
@RestController("platformShopDetailController")
@RequestMapping("/platform/shop_detail")
@Api(tags = "platform-店铺信息")
public class ShopDetailController {
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ShopDetailService shopDetailService;

    @Autowired
    private MapperFacade mapperFacade;

    @Autowired
    private AccountFeignClient accountFeignClient;

    @Autowired
    private ShopUserService shopUserService;

    @GetMapping("/page")
    @ApiOperation(value = "分页查询", notes = "分页查询")
    public ServerResponseEntity<PageVO<ShopDetailVO>> getShopAuditingPage(PageDTO pageDTO, ShopDetailDTO shopDetailDTO) {
        if (!Objects.equals(Constant.PLATFORM_SHOP_ID, AuthUserContext.get().getTenantId())) {
            throw new Mall4cloudException(ResponseEnum.UNAUTHORIZED);
        }
        return ServerResponseEntity.success(shopDetailService.page(pageDTO, shopDetailDTO));
    }

    @GetMapping("/info")
    @ApiOperation(value = "店铺详情", notes = "店铺详情")
    public ServerResponseEntity<ShopDetailVO> getInfo(@RequestParam Long shopId) {
        ShopDetailVO shopDetailVO = shopDetailService.getByShopId(shopId);
        return ServerResponseEntity.success(shopDetailVO);
    }

    /**
     * 注册店铺
     */
    @PostMapping("/ua/create_shops")
    @ApiOperation(value = "注册店铺", notes = "注册店铺")
    public ServerResponseEntity<Object> createShops(@RequestBody AuthDTO authDTO) {
       ServerResponseEntity<UserInfoInTokenBO> userInfoInTokenResponse=shopDetailService.createShops(authDTO);
        if (!userInfoInTokenResponse.isSuccess()) {
            return ServerResponseEntity.transform(userInfoInTokenResponse);
        }
        UserInfoInTokenBO userInfoInTokenBO = userInfoInTokenResponse.getData();
        ServerResponseEntity<TokenInfoVO> TokenInfoVO=accountFeignClient.storeTokenAndGetVo(userInfoInTokenBO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","添加成功");
        map.put("token",TokenInfoVO.getData());
        return ServerResponseEntity.success(map);
    }
    /**
     * 注册店铺2基本信息
     */
    @PostMapping("/update_shops_few")
    @ApiOperation(value = "注册店铺,二、添加基本信息", notes = "注册店铺")
    public ServerResponseEntity<Object> basicInformation(@RequestBody BasicInformationDTO basicInformationDTO) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        shopDetailService.creatBasicInformation(userInfoInTokenBO,basicInformationDTO);
        ServerResponseEntity<TokenInfoVO> tokenInfoVO=accountFeignClient.storeTokenAndGetVo(userInfoInTokenBO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","添加成功");
        map.put("token",tokenInfoVO);
        return ServerResponseEntity.success(map);
    }

    @PutMapping("/update_shop")
    @ApiOperation(value = "更新店铺", notes = "更新店铺")
    public ServerResponseEntity<Void> updateShop(@RequestBody ShopDetailDTO shopDetailDTO) {
        shopDetailService.update(mapperFacade.map(shopDetailDTO, ShopDetail.class));
        return ServerResponseEntity.success();
    }
  /*
  *  注册店铺3资质信息
  * */
    @PostMapping("/create_shop_aptitude")
    @ApiOperation(value = "注册店铺3资质信息", notes = "注册店铺3资质信息")
    public ServerResponseEntity<Void> createShopAptitude(@RequestBody ShopAptitudeDTO shopAptitudeDTO) {
        return ServerResponseEntity.success();
    }

}
