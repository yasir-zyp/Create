/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.mall4j.cloud.payment.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

import com.github.binarywang.wxpay.v3.auth.AutoUpdateCertificatesVerifier;
import com.github.binarywang.wxpay.v3.auth.WxPayCredentials;
import com.mall4j.cloud.payment.config.bean.WxPay;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

/**
 * 微信公众号配置文件
 *
 * @author LGH
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(WxPayService.class)
public class WxPayConfiguration {

    private final WxPay wxPay;
    @Value("${spring.profiles.active}")
    private String profile;

    @Bean
    public WxPayService wxMpPayService() {
        return getWxMpPayServiceByAppId(wxPay.getAppId());
    }
    private WxPayService getWxMpPayServiceByAppId(String appid) {
        String mchSerialNo=wxPay.getMchSerialNo();
        String apiV3Key=wxPay.getMchKey();
        // 加载商户私钥（privateKey：私钥字符串）
        PrivateKey merchantPrivateKey = null;


        try {
            merchantPrivateKey = PemUtil.loadPrivateKey(
                    new FileInputStream(wxPay.getPrivateKeyPath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
     String mchId=wxPay.getMchId();

        CertificatesManager certificatesManager = CertificatesManager.getInstance();

        try {
            certificatesManager.putMerchant(mchId, new WechatPay2Credentials(mchId,
                    new PrivateKeySigner(mchSerialNo, merchantPrivateKey)), apiV3Key.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (HttpCodeException e) {
            e.printStackTrace();
        }
        Verifier verifier=null;
        try {
             verifier = certificatesManager.getVerifier(mchId);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
// ... 若有多个商户号，可继续调用putMerchant添加商户信息
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, wxPay.getMchSerialNo(), merchantPrivateKey)
                .withValidator(new WechatPay2Validator(verifier));
// ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient

// 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
        CloseableHttpClient httpClient = builder.build();
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setApiV3HttpClient(httpClient);
        payConfig.setAppId(appid);
        payConfig.setMchId(mchId);
        payConfig.setApiV3Key(apiV3Key);
        payConfig.setCertSerialNo(mchSerialNo);
        payConfig.setNotifyUrl(WxConfig.noticeUrl);
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }
}
