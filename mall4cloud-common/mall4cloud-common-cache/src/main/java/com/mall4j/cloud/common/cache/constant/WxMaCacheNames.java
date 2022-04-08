package com.mall4j.cloud.common.cache.constant;

/**
 * @author zyp
 *
 */
public interface WxMaCacheNames {
    /**
     * 前缀
     */
    String WxMa_PREFIX = "wxMa:";


    /**
     * 小程序token缓存
     */

    String ACCESS_TOKEN_KEY = WxMa_PREFIX +"access_token:";

    String SESSION_KEY = WxMa_PREFIX +"session_keys:";


    String JSAPI_TICKET_KEY = WxMa_PREFIX +"jsapi_ticket:";

    String CARD_API_TICKET_KEY = WxMa_PREFIX +"card_api_ticket:";

}
