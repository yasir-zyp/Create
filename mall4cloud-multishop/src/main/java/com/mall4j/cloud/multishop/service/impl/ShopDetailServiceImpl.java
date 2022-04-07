package com.mall4j.cloud.multishop.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.ISelect;
import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.api.auth.constant.SysTypeEnum;
import com.mall4j.cloud.api.auth.dto.AuthAccountDTO;
import com.mall4j.cloud.api.auth.feign.AccountFeignClient;
import com.mall4j.cloud.api.auth.vo.AuthAccountVO;
import com.mall4j.cloud.api.feign.SearchSpuFeignClient;
import com.mall4j.cloud.api.multishop.bo.EsShopDetailBO;
import com.mall4j.cloud.api.product.vo.SpuVO;
import com.mall4j.cloud.api.rbac.feign.UserRoleFeignClient;
import com.mall4j.cloud.api.user.feign.UserFeignClient;
import com.mall4j.cloud.api.vo.search.SpuSearchVO;
import com.mall4j.cloud.common.cache.constant.CacheNames;
import com.mall4j.cloud.common.cache.util.RedisUtil;
import com.mall4j.cloud.common.constant.Constant;
import com.mall4j.cloud.common.constant.UserAdminType;
import com.mall4j.cloud.common.constant.StatusEnum;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.util.PageUtil;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.common.util.IpHelper;
import com.mall4j.cloud.common.util.PrincipalUtil;
import com.mall4j.cloud.multishop.constant.ShopStatus;
import com.mall4j.cloud.multishop.constant.ShopType;
import com.mall4j.cloud.multishop.constant.UniversalStatus;
import com.mall4j.cloud.multishop.dto.*;
import com.mall4j.cloud.multishop.mapper.*;
import com.mall4j.cloud.multishop.model.*;
import com.mall4j.cloud.multishop.service.ShopAddrService;
import com.mall4j.cloud.multishop.service.ShopDetailService;
import com.mall4j.cloud.api.multishop.vo.ShopDetailVO;
import com.mall4j.cloud.multishop.service.ShopUserService;
import com.mall4j.cloud.multishop.vo.*;
import io.seata.spring.annotation.GlobalTransactional;
import ma.glasnost.orika.MapperFacade;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 店铺详情
 *
 * @author FrozenWatermelon
 * @date 2020-11-23 16:24:29
 */
@Service
public class ShopDetailServiceImpl implements ShopDetailService {

    @Autowired
    private ShopDetailMapper shopDetailMapper;
    @Autowired
    private SearchSpuFeignClient searchSpuFeignClient;
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountFeignClient accountFeignClient;
    @Autowired
    private ShopUserMapper shopUserMapper;
    @Autowired
    private ShopQualificationMapper shopQualificationMapper;
    @Autowired
    private ShopAddrService shopAddrService;
    @Autowired
    private ShopAddrMapper shopAddrMapper;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private GatheringInfoMapper gatheringInfoMapper;
    @Override
    public PageVO<ShopDetailVO> page(PageDTO pageDTO, ShopDetailDTO shopDetailDTO) {
        return PageUtil.doPage(pageDTO, () -> shopDetailMapper.list(shopDetailDTO));
    }

    @Override
    @Cacheable(cacheNames = CacheNames.SHOP_DETAIL_ID_KEY, key = "#shopId")
    public ShopDetailVO getByShopId(Long shopId) {
        ServerResponseEntity<AuthAccountVO> accountRes = accountFeignClient.getMerchantInfoByTenantId(shopId);
        if (!accountRes.isSuccess()) {
            throw new Mall4cloudException("商家信息获取失败");
        }
        AuthAccountVO authAccountVO = accountRes.getData();
        ShopDetailVO shopDetailVO = shopDetailMapper.getByShopId(shopId);
        if (Objects.nonNull(authAccountVO)) {
            shopDetailVO.setUsername(authAccountVO.getUsername());
        }
        return shopDetailVO;
    }

    @Override
    public void save(ShopDetail shopDetail) {
        shopDetailMapper.save(shopDetail);
    }

    @Override
    @CacheEvict(cacheNames = CacheNames.SHOP_DETAIL_ID_KEY, key = "#shopDetail.shopId")
    public void update(ShopDetail shopDetail,UserInfoInTokenBO userInfoInTokenBO) {
        shopDetail.setShopId(userInfoInTokenBO.getTenantId());
        if (shopDetail.getShopName()!=null){
           ShopUser shopUser=new ShopUser();
           shopUser.setShopUserId(userInfoInTokenBO.getUserId());
           shopUser.setNickName(shopDetail.getShopName());
           shopUser.setShopId(userInfoInTokenBO.getTenantId());
           shopUserMapper.update(shopUser);
        }
        shopDetailMapper.update(shopDetail);
    }

    @Override
    @CacheEvict(cacheNames = CacheNames.SHOP_DETAIL_ID_KEY, key = "#shopId")
    public void deleteById(Long shopId) {
        shopDetailMapper.deleteById(shopId);
    }

    @Override
    public List<ShopDetail> listByShopIds(List<Long> shopIds) {
        if (CollUtil.isEmpty(shopIds)) {
            return new ArrayList<>();
        }
        return shopDetailMapper.listByShopIds(shopIds);
    }

    @Override
    public PageVO<ShopDetailAppVO> shopSearchPage(PageDTO pageDTO, ShopDetailDTO shopDetailDTO) {
        PageVO<ShopDetailAppVO> page = PageUtil.doPage(pageDTO, () -> shopDetailMapper.shopSearchList(shopDetailDTO));
        Set<Long> spuIdSet = page.getList().stream().map(ShopDetailAppVO::getShopId).collect(Collectors.toSet());
        ServerResponseEntity<List<SpuSearchVO>> spuResponse = searchSpuFeignClient.limitSizeListByShopIds(new ArrayList<>(spuIdSet), Constant.SPU_SIZE_FIVE);
        if (!Objects.equals(spuResponse.getCode(), ResponseEnum.OK.value())) {
            throw new Mall4cloudException(spuResponse.getMsg());
        } else if (CollectionUtil.isEmpty(spuResponse.getData())) {
            return page;
        }
        List<SpuSearchVO> data = spuResponse.getData();
        Map<Long, List<SpuSearchVO>> shopMap = data.stream().collect(Collectors.groupingBy(SpuSearchVO::getShopId));
        for (ShopDetailAppVO shopDetail : page.getList()) {
            shopDetail.setSpuList(shopMap.get(shopDetail.getShopId()));
        }
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional
    public void applyShop(ShopDetailDTO shopDetailDTO) {
        checkShopInfo(shopDetailDTO);
        ShopDetail newShopDetail = mapperFacade.map(shopDetailDTO, ShopDetail.class);
        // 申请开店
        newShopDetail.setShopStatus(ShopStatus.OPEN.value());
        newShopDetail.setType(ShopType.STOP.value());
        shopDetailMapper.save(newShopDetail);
        shopDetailDTO.setShopId(newShopDetail.getShopId());
        // 创建账号
        createShopAccount(shopDetailDTO, StatusEnum.ENABLE);
    }

    @Override
    public void changeSpuStatus(Long shopId, Integer shopStatus) {
        shopDetailMapper.changeSpuStatus(shopId, shopStatus);
    }

    @Override
    @CacheEvict(cacheNames = CacheNames.SHOP_DETAIL_ID_KEY, key = "#shopId")
    public void removeCacheByShopId(Long shopId) {

    }

    @Override
    public EsShopDetailBO shopExtensionData(Long shopId) {
        return shopDetailMapper.shopExtensionData(shopId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional(rollbackFor = Exception.class)
    public ServerResponseEntity<Object> createShop(ShopDetailDTO shopDetailDTO) {
 /*       checkShopInfo(shopDetailDTO);
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        if (Objects.nonNull(userInfoInTokenBO.getTenantId())) {
            throw new Mall4cloudException("该用户已经创建过店铺");
        }*/
        //判断用户名账号密码是否为空
        if (StrUtil.isBlank(shopDetailDTO.getUsername())) {
            return ServerResponseEntity.showFailMsg("用户名不能为空");
        }
        if (StrUtil.isBlank(shopDetailDTO.getPassword())) {
            return ServerResponseEntity.showFailMsg("密码不能为空");
        }
        //判断验证码是否输入正确
        String codes= RedisUtil.getLongValues(shopDetailDTO.getAccountPhone());
        if (!codes.equals(shopDetailDTO.getCode())){
            return ServerResponseEntity.showFailMsg("验证码不正确");
        }
        //加密密码
        String password=passwordEncoder.encode(shopDetailDTO.getPassword());
        // 保存店铺
        ShopDetail shopDetail = mapperFacade.map(shopDetailDTO, ShopDetail.class);
        shopDetail.setShopStatus(ShopStatus.OPEN.value());
        shopDetailMapper.save(shopDetail);
        // 保存商家账号
        // 保存到shopUser
        ShopUser shopUser = new ShopUser();
        shopUser.setShopId(shopDetail.getShopId());
        shopUser.setHasAccount(1);
        shopUser.setNickName("暂无");
        shopUser.setPhoneNum(shopDetailDTO.getAccountPhone());
        shopUserService.save(shopUser, null);
        // 保存到authAccount
        AuthAccountDTO authAccountDTO = new AuthAccountDTO();
        authAccountDTO.setTenantId(shopDetail.getShopId());
        authAccountDTO.setUsername(shopDetailDTO.getUsername());
        authAccountDTO.setPassword(password);
        authAccountDTO.setCreateIp(IpHelper.getIpAddr());
        authAccountDTO.setStatus(StatusEnum.ENABLE.value());
        authAccountDTO.setSysType(SysTypeEnum.MULTISHOP.value());
        authAccountDTO.setIsAdmin(UserAdminType.ADMIN.value());
        authAccountDTO.setUserId(shopUser.getShopUserId());
        authAccountDTO.setAccountPhone(shopDetailDTO.getAccountPhone());
        accountFeignClient.save(authAccountDTO);

       /* userInfoInTokenBO.setTenantId(shopDetail.getShopId());
        ServerResponseEntity<Void> updateTenantIdRes = accountFeignClient.updateUserInfoByUserIdAndSysType(userInfoInTokenBO, AuthUserContext.get().getUserId(), SysTypeEnum.ORDINARY.value());
        if (!Objects.equals(updateTenantIdRes.getCode(), ResponseEnum.OK.value())) {
            throw new Mall4cloudException(updateTenantIdRes.getMsg());
        }*/
        return ServerResponseEntity.success();
    }

    @Override
    public List<ShopDetailVO> getShopDetailByShopIdAndShopName(List<Long> shopIds, String shopName) {
        return shopDetailMapper.getShopDetailByShopIdAndShopName(shopIds,shopName);
    }

    @Override
    public Boolean checkShopName(String shopName) {
        int count = shopDetailMapper.countShopName(shopName, null);
        return count <= 0;
    }


    /**
     * 检验店铺信息是否正确
     * @param shopDetailDTO
     */
    private void checkShopInfo(ShopDetailDTO shopDetailDTO) {
        // 店铺名称
        if (StrUtil.isNotBlank(shopDetailDTO.getShopName())) {
            shopDetailDTO.setShopName(shopDetailDTO.getShopName().trim());
        }
        if(shopDetailMapper.countShopName(shopDetailDTO.getShopName(), null) > 0) {
            throw new Mall4cloudException("店铺名称已存在");
        }

        String username = shopDetailDTO.getUsername();
        // 用户名
        if (!PrincipalUtil.isUserName(username)) {
            throw new Mall4cloudException("用户名格式不正确");
        }

        ServerResponseEntity<AuthAccountVO> accountResponse = accountFeignClient.getByUsernameAndSysType(username, SysTypeEnum.MULTISHOP);
        if (!Objects.equals(accountResponse.getCode(), ResponseEnum.OK.value())) {
            throw new Mall4cloudException(accountResponse.getMsg());
        }

        AuthAccountVO authAccountVO = accountResponse.getData();
        if (Objects.nonNull(authAccountVO)) {
            throw new Mall4cloudException("用户账号已存在");
        }
    }

    /**
     * 创建店铺初始账号
     * @param shopDetailDTO
     * @param statusEnum
     */
    public void createShopAccount(ShopDetailDTO shopDetailDTO, StatusEnum statusEnum) {
        ShopUser shopUser = new ShopUser();
        shopUser.setShopId(shopDetailDTO.getShopId());
        shopUser.setHasAccount(1);
        shopUser.setNickName(shopDetailDTO.getUsername());
        shopUserService.save(shopUser, null);

        AuthAccountDTO authAccountDTO = new AuthAccountDTO();
        authAccountDTO.setUsername(shopDetailDTO.getUsername());
        String password = passwordEncoder.encode(shopDetailDTO.getPassword().trim());
        authAccountDTO.setPassword(password);
        authAccountDTO.setStatus(statusEnum.value());
        authAccountDTO.setSysType(SysTypeEnum.MULTISHOP.value());
        authAccountDTO.setCreateIp(IpHelper.getIpAddr());
        authAccountDTO.setTenantId(shopDetailDTO.getShopId());
        authAccountDTO.setUserId(shopUser.getShopUserId());
        authAccountDTO.setIsAdmin(UserAdminType.ADMIN.value());
        ServerResponseEntity<Long> save = accountFeignClient.save(authAccountDTO);
        if (!Objects.equals(save.getCode(), ResponseEnum.OK.value())) {
            throw new Mall4cloudException(save.getMsg());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional(rollbackFor = Exception.class)
    public ServerResponseEntity<UserInfoInTokenBO> createShops(AuthDTO authDTO) {
 /*       checkShopInfo(shopDetailDTO);
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        if (Objects.nonNull(userInfoInTokenBO.getTenantId())) {
            throw new Mall4cloudException("该用户已经创建过店铺");
        }*/
        ShopDetailDTO shopDetailDTO=new ShopDetailDTO();
        shopDetailDTO.setUsername(authDTO.getUsername());
        shopDetailDTO.setPassword(authDTO.getPassword());
        shopDetailDTO.setAccountPhone(authDTO.getAccountPhone());
        shopDetailDTO.setCode(authDTO.getCode());
        //判断用户名账号密码是否为空
        if (StrUtil.isBlank(shopDetailDTO.getUsername())) {
            return ServerResponseEntity.showFailMsg("用户名不能为空");
        }
        if (StrUtil.isBlank(shopDetailDTO.getPassword())) {
            return ServerResponseEntity.showFailMsg("密码不能为空");
        }
        ServerResponseEntity<AuthAccountVO> serverResponseEntity=accountFeignClient.getByUsernameByName(shopDetailDTO.getUsername());
        // 用户名
        if (!PrincipalUtil.isUserName(shopDetailDTO.getUsername())) {
            return ServerResponseEntity.showFailMsg("用户名格式不正确");
        }
        if (serverResponseEntity.getData()!=null){
            return ServerResponseEntity.showFailMsg("用户名已存在");
        }
        //判断验证码是否输入正确
        String codes= RedisUtil.getLongValues(shopDetailDTO.getAccountPhone());
        if (codes==null){
            return ServerResponseEntity.showFailMsg("未发送验证码");
        }
        if (!codes.equals(shopDetailDTO.getCode())){
            return ServerResponseEntity.showFailMsg("验证码不正确");
        }


        // 保存店铺
        ShopDetail shopDetail = mapperFacade.map(shopDetailDTO, ShopDetail.class);
        shopDetail.setShopStatus(ShopStatus.OPEN.value());
        shopDetailMapper.save(shopDetail);

        // 保存商家账号
        // 保存到shopUser
        ShopUser shopUser = new ShopUser();
        shopUser.setShopId(shopDetail.getShopId());
        shopUser.setHasAccount(1);
        shopUser.setNickName("暂无");
        shopUser.setPhoneNum(shopDetailDTO.getAccountPhone());
        shopUserService.save(shopUser, null);
        // 保存到authAccount
        AuthAccountDTO authAccountDTO = new AuthAccountDTO();
        authAccountDTO.setTenantId(shopDetail.getShopId());
        authAccountDTO.setUsername(shopDetailDTO.getUsername());
        authAccountDTO.setPassword(shopDetailDTO.getPassword());
        authAccountDTO.setCreateIp(IpHelper.getIpAddr());
        authAccountDTO.setStatus(StatusEnum.ENABLE.value());
        authAccountDTO.setSysType(SysTypeEnum.MULTISHOP.value());
        authAccountDTO.setIsAdmin(UserAdminType.ADMIN.value());
        authAccountDTO.setUserId(shopUser.getShopUserId());
        authAccountDTO.setAccountPhone(shopDetailDTO.getAccountPhone());
        accountFeignClient.save(authAccountDTO);
        //获取封装token需要的信息
        UserInfoInTokenBO userInfoInTokenBO;
        userInfoInTokenBO=mapperFacade.map(authAccountDTO, UserInfoInTokenBO.class);
       /* userInfoInTokenBO.setTenantId(shopDetail.getShopId());
        ServerResponseEntity<Void> updateTenantIdRes = accountFeignClient.updateUserInfoByUserIdAndSysType(userInfoInTokenBO, AuthUserContext.get().getUserId(), SysTypeEnum.ORDINARY.value());
        if (!Objects.equals(updateTenantIdRes.getCode(), ResponseEnum.OK.value())) {
            throw new Mall4cloudException(updateTenantIdRes.getMsg());
        }*/
        return ServerResponseEntity.success(userInfoInTokenBO);
    }

    @Override
    public void creatBasicInformation(UserInfoInTokenBO userInfoInTokenBO, BasicInformationDTO basicInformationDTO) {
        ShopDetail shopDetail = mapperFacade.map(basicInformationDTO, ShopDetail.class);
        shopDetail.setShopId(userInfoInTokenBO.getTenantId());
        if (basicInformationDTO.getAreaId()!=null){
        int array[]=basicInformationDTO.getAreaId();
        shopDetail.setProvinceId(array[0]);
        shopDetail.setCityId(array[1]);
        shopDetail.setAreaIds(array[2]);
        }
        shopDetailMapper.update(shopDetail);
        if (shopDetail.getShopName()!=null){
            ShopUser shopUser=new ShopUser();
            shopUser.setShopUserId(userInfoInTokenBO.getUserId());
            shopUser.setShopId(userInfoInTokenBO.getTenantId());
            shopUser.setNickName(shopDetail.getShopName());
            shopUserMapper.update(shopUser);
        }
    }

    @Override
    public List<ShopQualificationVO> creatShopQualification(List<ShopQualificationDTO> shopQualificationDTOList, UserInfoInTokenBO userInfoInTokenBO) {
        for (int i = 0; i <shopQualificationDTOList.size() ; i++) {
            if (shopQualificationDTOList.get(i).getShopQualificationId()==null){
            ShopQualification shopQualification=mapperFacade.map(shopQualificationDTOList.get(i), ShopQualification.class);
            shopQualification.setShopId(userInfoInTokenBO.getTenantId());
            shopQualification.setStatus(UniversalStatus.USE.value());
            shopQualificationMapper.insertSelective(shopQualification);
            }
           else {
                ShopQualification shopQualification = mapperFacade.map(shopQualificationDTOList.get(i), ShopQualification.class);
                shopQualificationMapper.updateByPrimaryKeySelective(shopQualification);
            }
        }
        return shopQualificationMapper.findAptitudeByToken(userInfoInTokenBO.getTenantId());
    }

    @Override
    public ServerResponseEntity<UserInfoInTokenBO> updateShops(AuthDTO authDTO) {
        //获取token
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        //通过用户名获取用户信息
        ServerResponseEntity<AuthAccountVO> serverResponseEntity=accountFeignClient.getByUsernameByName(authDTO.getUsername());
        AuthAccountDTO authAccountDTO = new AuthAccountDTO();
        authAccountDTO.setUserId(userInfoInTokenBO.getUserId());
        //判断验证码是否输入正确
        String codes= RedisUtil.getLongValues(authDTO.getAccountPhone());
        if (!codes.equals(authDTO.getCode())){
            return ServerResponseEntity.showFailMsg("验证码不正确");
        }
        if (authDTO.getUsername()!=null){
            if (!PrincipalUtil.isUserName(authDTO.getUsername())) {
                return ServerResponseEntity.showFailMsg("用户名格式不正确");
            }
            if (serverResponseEntity.getData()!=null){
                return ServerResponseEntity.showFailMsg("用户名已存在");
            }
            authAccountDTO.setUsername(authDTO.getUsername());
        }
        if (authDTO.getPassword()!=null){
            String password=passwordEncoder.encode(authDTO.getPassword());
            authAccountDTO.setPassword(password);
        }
        authAccountDTO.setSysType(SysTypeEnum.MULTISHOP.value());
        authAccountDTO.setUserId(userInfoInTokenBO.getUserId());
        /*
        * 将电话号码存进user表
        * */
        if (authDTO.getAccountPhone()!=null){
        ShopUser shopUser=new ShopUser();
        shopUser.setPhoneNum(authDTO.getAccountPhone());
        shopUser.setShopUserId(userInfoInTokenBO.getUserId());
        shopUser.setShopId(userInfoInTokenBO.getTenantId());
        shopUserMapper.update(shopUser);
        }
        accountFeignClient.update(authAccountDTO);
        return ServerResponseEntity.success(userInfoInTokenBO);
    }

    @Override
    public void deleteAptitude(Long shopQualificationId) {
        shopQualificationMapper.deleteByPrimaryKey(shopQualificationId);
    }

    @Override
    public ShopDetaislVO findShopByToken(Long tenantId) {
        return shopDetailMapper.findShopByToken(tenantId);
    }

    @Override
    public List<ShopQualificationVO> findAptitudeByToken(Long tenantId) {
        return shopQualificationMapper.findAptitudeByToken(tenantId);
    }

    @Override
    public void creatBusinessAddress(ShopAddrDTO shopAddrDTO) {
        Long id=null;
        //获取token
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        ShopAddr shopAddr=mapperFacade.map(shopAddrDTO, ShopAddr.class);
        //拆分省市区
        int array[]=shopAddrDTO.getAreaId();
        if (shopAddrDTO.getAreaId()!=null){
            shopAddr.setProvinceId((long) array[0]);
            shopAddr.setCityId((long) array[1]);
            shopAddr.setAreaIds((long) array[2]);
        }
        shopAddr.setShopId(userInfoInTokenBO.getTenantId());
        //通过id查询省市区名称
        id=shopAddr.getProvinceId();
        String provinceName=userFeignClient.findNameById(id);
        shopAddr.setProvinceName(provinceName);
        id=shopAddr.getCityId();
        String cityName=userFeignClient.findNameById(id);
        shopAddr.setCityName(cityName);
        id=shopAddr.getAreaIds();
        String areaName=userFeignClient.findNameById(id);
        shopAddr.setAreaName(areaName);
        /*清除默认地址*/
        if (shopAddr.getIsDefault().equals(UniversalStatus.USE.value())) {
            shopAddrMapper.updateIsDefault(shopAddr.getShopId());
        }
        shopAddrMapper.insertSelective(shopAddr);
    }

    @Override
    public PageVO<ShopAddrVO> findBusinessAddress(PageDTO pageDTO) {
        UserInfoInTokenBO userInfoInTokenBO=AuthUserContext.get();
        List<ShopAddrVO> shopAddrVOList=shopAddrService.findBusinessAddress(userInfoInTokenBO.getTenantId());
        return PageUtil.doPage(pageDTO, ()->shopAddrService.findBusinessAddress(userInfoInTokenBO.getTenantId()));
    }

    @Override
    public void delBusinessAddress(Long shopAddrId) {
        shopAddrMapper.deleteByPrimaryKey(shopAddrId);
    }

    @Override
    public void upBusinessAddress(ShopAddrDTO shopAddrDTO) {
        ShopAddr shopAddr=mapperFacade.map(shopAddrDTO, ShopAddr.class);
        int array[]=shopAddrDTO.getAreaId();
        shopAddr.setProvinceId((long) array[0]);
        shopAddr.setCityId((long) array[1]);
        shopAddr.setAreaIds((long) array[2]);
        /*清除默认地址*/
        if (shopAddr.getIsDefault().equals(UniversalStatus.USE.value())) {
            shopAddrMapper.updateIsDefault(shopAddr.getShopId());
        }
        shopAddrMapper.updateByPrimaryKeySelective(shopAddr);
    }

    @Override
    public ShopAddrVO echoBusinessAddress(Long shopAddrId) {
        ShopAddrVO shopAddrVO=shopAddrMapper.selectByPrimaryKeys(shopAddrId);
        Integer provinceId= Math.toIntExact(shopAddrVO.getProvinceId());
        Integer cityId= Math.toIntExact(shopAddrVO.getCityId());
        Integer areaId= Math.toIntExact(shopAddrVO.getAreaIds());
        int array[] ={provinceId,cityId,areaId};
        shopAddrVO.setAreaId(array);
        return shopAddrVO;
    }

    @Override
    public void creatAccountInfor(GatheringInfoDTO gatheringInfoDTO) {
        UserInfoInTokenBO userInfoInTokenBO=AuthUserContext.get();
        GatheringInfo gatheringInfo=mapperFacade.map(gatheringInfoDTO, GatheringInfo.class);
        gatheringInfo.setShopId(userInfoInTokenBO.getTenantId());
        gatheringInfoMapper.insertSelective(gatheringInfo);
    }

    @Override
    public PageVO<GatheringInfoVO> findAccountInfor(PageDTO pageDTO) {
        UserInfoInTokenBO userInfoInTokenBO=AuthUserContext.get();
        return PageUtil.doPage(pageDTO, ()->gatheringInfoMapper.findAccountInfor(userInfoInTokenBO.getTenantId()));
    }

    @Override
    public void delAccountInfor(Long gatheringInfoId) {
        gatheringInfoMapper.deleteByPrimaryKey(gatheringInfoId);
    }

    @Override
    public void upAccountInfor(GatheringInfoDTO gatheringInfoDTO) {
        GatheringInfo gatheringInfo=mapperFacade.map(gatheringInfoDTO, GatheringInfo.class);
        gatheringInfoMapper.updateByPrimaryKeySelective(gatheringInfo);
    }

    @Override
    public GatheringInfoVO echoAccountInfor(Long gatheringInfoId) {
        GatheringInfoVO gatheringInfoVO=gatheringInfoMapper.selectByPrimaryKeys(gatheringInfoId);
        return gatheringInfoVO;
    }

}
