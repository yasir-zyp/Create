package com.mall4j.cloud.multishop.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class GatheringInfo implements Serializable {
    /*主键*/
    private Long gatheringInfoId;
    /*支付类型（0支付宝，1微信，2银行卡）每个企业可以每样提供一个，可以修改*/
    private Long paymentType;
    /*账号*/
    private String theAccount;
    /*户名*/
    private String accountName;
    /*开户行*/
    private String openingBank;
    /*所属企业*/
    private Long shopId;

    private static final long serialVersionUID = 1L;
}