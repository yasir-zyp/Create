package com.mall4j.cloud.auth.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.util.StrUtil;
import com.mall4j.cloud.auth.dto.AuthGetPhoneDTO;
import com.mall4j.cloud.auth.service.WechatService;
import com.mall4j.cloud.common.cache.constant.CacheNames;
import com.mall4j.cloud.common.cache.util.RedisUtil;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ResponseEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class WechatServiceImpl implements WechatService {
    @Autowired
    WxMaService wxMaService;
    @Autowired
    WxMaUserService wxMaUserService;
    @Override
    @Cacheable(cacheNames = CacheNames.ACCESS_TOKEN_KEY, key = "'accessToken'")
    public String getAccessToken() {
        String accessToken="";

        try {
            accessToken=  wxMaService.getAccessToken();
        }catch (Exception e){
            throw new Mall4cloudException("微信获取token异常");
        }

        return accessToken;
    }

    @Override
    public String getPhone(AuthGetPhoneDTO authGetPhoneDTO) {
        String  key =CacheNames.SESSION_KEY+authGetPhoneDTO.getUnionId();
        String sessionKey=RedisUtil.getLongValues(key);
        if(StrUtil.isBlank(sessionKey)){
            throw new Mall4cloudException(ResponseEnum.BIZ_TEMP_SESSION_KEY_EXPIRE);
        }

        WxMaPhoneNumberInfo wxMaPhoneNumberInfo =wxMaUserService.getPhoneNoInfo(sessionKey,authGetPhoneDTO.getEncryptedData(),authGetPhoneDTO.getIv());
        String phone=wxMaPhoneNumberInfo.getPhoneNumber();
        return phone;
    }

    @Override
    public String getOpenId(String JSCODE) {
        WxMaJscode2SessionResult wxMaJscode2SessionResult=null;
        try {
            wxMaJscode2SessionResult=wxMaUserService.getSessionInfo(JSCODE);
        }catch (Exception e){
            throw  new Mall4cloudException("微信获取openId异常");
        }
        String openid=wxMaJscode2SessionResult.getOpenid();
        String sessionKey=wxMaJscode2SessionResult.getSessionKey();
        String  key =CacheNames.SESSION_KEY+openid;
        long second = 3600L;
        RedisUtil.set(key,sessionKey,second);
        String unionId=wxMaJscode2SessionResult.getUnionid();
        return openid;
    }



}
