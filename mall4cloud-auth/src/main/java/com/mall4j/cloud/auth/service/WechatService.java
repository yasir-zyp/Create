package com.mall4j.cloud.auth.service;

import com.mall4j.cloud.auth.dto.AuthGetPhoneDTO;

public interface WechatService {

    /**
     * 获取微信AccessToken
     * @return
     */
    String getAccessToken();

    /**
     * 获取用户手机号
     * @param
     * @return
     */
    String getPhone(AuthGetPhoneDTO authGetPhoneDTO);

    /**
     * 通过jsCode获取openId
     * @param jscode
     * @return
     */
    String getOpenId(String jscode);

}
