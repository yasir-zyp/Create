package com.mall4j.cloud.multishop.controller.platform;

import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
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
import com.mall4j.cloud.multishop.dto.AuthDTO;
import com.mall4j.cloud.multishop.dto.BasicInformationDTO;
import com.mall4j.cloud.multishop.dto.ShopQualificationDTO;
import com.mall4j.cloud.multishop.dto.ShopDetailDTO;
import com.mall4j.cloud.multishop.manager.TokenStore;
import com.mall4j.cloud.multishop.model.ShopDetail;
import com.mall4j.cloud.multishop.service.ShopDetailService;
import com.mall4j.cloud.multishop.service.ShopUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    @ApiOperation(value = "注册店铺,一、用户信息", notes = "注册店铺")
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
        map.put("authDTO",authDTO);
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
        map.put("basicInformationDTO",basicInformationDTO);
        return ServerResponseEntity.success(map);
    }
    @PutMapping("/update_shop")
    @ApiOperation(value = "更新店铺,二、修改基本信息", notes = "更新店铺")
    public ServerResponseEntity<Object> updateShop(@RequestBody BasicInformationDTO basicInformationDTO) {
        //获取token
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        shopDetailService.update(mapperFacade.map(basicInformationDTO, ShopDetail.class),userInfoInTokenBO);
        ServerResponseEntity<TokenInfoVO> tokenInfoVO=accountFeignClient.storeTokenAndGetVo(userInfoInTokenBO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","修改成功");
        map.put("token",tokenInfoVO);
        map.put("basicInformationDTO",basicInformationDTO);
        return ServerResponseEntity.success(map);
    }
  /*
  *  注册店铺3资质信息
  * */
    @PostMapping("/create_shop_aptitude")
    @ApiOperation(value = "注册店铺,三、资质信息", notes = "注册店铺")
    public ServerResponseEntity<Object> createShopAptitude(@RequestBody List<ShopQualificationDTO> shopQualificationDTOList) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        shopDetailService.creatShopQualification(shopQualificationDTOList,userInfoInTokenBO);
        Map<String,Object> map=new HashMap<>();
        ServerResponseEntity<TokenInfoVO> tokenInfoVO=accountFeignClient.storeTokenAndGetVo(userInfoInTokenBO);
        map.put("msg","添加成功");
        map.put("token",tokenInfoVO);
        map.put("shopQualificationDTOList",shopQualificationDTOList);
        return ServerResponseEntity.success(map);
    }


  /*
   * 修改注册店铺第一阶段的信息
   * */
   @PostMapping("/create_shops_repeat")
   @ApiOperation(value = "更新店铺,一、用户信息", notes = "修改注册信息第一阶段")
   public ServerResponseEntity<Object> updateShops(@RequestBody AuthDTO authDTO) {
       ServerResponseEntity<UserInfoInTokenBO> userInfoInTokenResponse=shopDetailService.updateShops(authDTO);
       if (!userInfoInTokenResponse.isSuccess()) {
           return ServerResponseEntity.transform(userInfoInTokenResponse);
       }
       UserInfoInTokenBO userInfoInTokenBO = userInfoInTokenResponse.getData();
       ServerResponseEntity<TokenInfoVO> TokenInfoVO=accountFeignClient.storeTokenAndGetVo(userInfoInTokenBO);
       Map<String,Object> map=new HashMap<>();
       map.put("msg","修改成功");
       map.put("token",TokenInfoVO.getData());
       map.put("authDTO",authDTO);
       return ServerResponseEntity.success(map);
   }
   /*
   * 根据资质id删除资质信息
   * */
   @DeleteMapping
   @ApiOperation(value = "删除资质信息", notes = "根据资质id删除资质信息")
   public ServerResponseEntity<Object> DeleteAptitude(@RequestBody Long shopQualificationId) {
       shopDetailService.deleteAptitude(shopQualificationId);
       Map<String,Object> map=new HashMap<>();
       map.put("msg","删除成功");
       return ServerResponseEntity.success(map);
   }
}
