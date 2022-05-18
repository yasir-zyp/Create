package com.mall4j.cloud.payment.config;

public class WxConfig {
    //public static String gatewayUri="https://192.168.11.120/mall4cloud_payment";
    public static String gatewayUri="https://baonijian.gkjtsolid.com/mall4cloud_payment";
    public static String noticeUrl=gatewayUri+ "/ua/notice/pay/order";
    public static String appid= "wxfc85e760981b183b";
    public static String mch_id= "1625153713";
    public static String mchKey= "yasirzypgkjc2478Yasirzypgkjc2472";
    public static String keyPath= "src/main/resources/cert/apiclient_cert.p12";
    public static String mchSerialNo= "1F7CA86A042DEFB8241B3257D306E592F0ED3910";
    public static String privateKeyPath= "src/main/resources/cert/apiclient_key.pem";
}
