/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.mall4j.cloud.payment.config.bean;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:pay.properties")
@ConfigurationProperties(prefix = "pay")
public class WxPay {
    /**
     * appid
     */
    private String appId;
    /**
     * 微信支付mchId
     */
    private String mchId;
    
    /**
     * 微信支付v3mchKey
     */
    private String mchKey;

	/**
	 * 支付证书路径
	 */
	private String keyPath;
    /**
     * 商户证书序列号路径
     */
    private String mchSerialNo;
    /**
     * 私钥路径
     */
    private String privateKeyPath;

}
