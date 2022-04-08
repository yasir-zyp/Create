/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.mall4j.cloud.auth.config;

import cn.binarywang.wx.miniapp.api.WxMaService;

import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.api.impl.WxMaUserServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;

import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.mall4j.cloud.auth.config.bean.WxMiniApp;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信小程序配置文件
 * @author LGH
 */
@Configuration
@AllArgsConstructor
@ConditionalOnClass(WxMaService.class)
public class WxMaConfiguration {
    @Bean
    public WxMaConfig wxMaConfig(WxMiniApp wxMiniApp) {
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wxMiniApp.getAppid());
        config.setSecret(wxMiniApp.getSecret());
        return config;
    }

    @Bean
    public WxMaService wxMaService(WxMaConfig maConfig) {
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(maConfig);
        return service;
    }
    @Bean
    public WxMaUserService wxMaUserService(WxMaService wxMaService){
        WxMaUserService wxMaUserService=new WxMaUserServiceImpl(wxMaService);
        return wxMaUserService;
    }
}
