package com.mall4j.cloud.api.payment.bo;

import lombok.Data;

@Data
public class PayInfoBo {
    /**
     * 支付信息，如商品名称
     */
    private String body;

    /**
     * 支付单号
     */
    private Long payId;

    /**
     * 付款金额
     */
    private Long payAmount;

    /**
     * api回调域名
     */
    private String apiNoticeUrl;

    /**
     * 支付完成会跳地址
     */
    private String returnUrl;

    /**
     * 第三方用户id
     */
    private String bizUserId;

    private String timeStamp;
    private String nonceStr;
    private String appId;
    private String prepay;
    private String sign;
    /**
     * 签名类型
     */
    private String signType;
}
