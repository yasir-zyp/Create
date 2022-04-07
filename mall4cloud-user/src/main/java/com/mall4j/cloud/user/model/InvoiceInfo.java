package com.mall4j.cloud.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class InvoiceInfo implements Serializable {
    /*主键*/
    private Long invoiceId;

    /*是否需要发票*/
    private Integer needInvoice;

    /*发票抬头*/
    private String invoiceLooked;

    /*纳税人识别号*/
    private String taxpayerIdentificationNumber;

    /*开票类型*/
    private String invoiceMakeType;

    /*注册地址*/
    private String invoiceRegisteredAddress;

    /*开户行*/
    private String invoiceOpeningBank;

    /*银行账号*/
    private String invoiceBankAccount;

    /*注册电话*/
    private String invoiceRegisteredPhone;

    /*发票邮寄地址*/
    private String invoiceMailingAddress;

    /*创建时间*/
    private Date createTime;

    /*更新时间*/
    private Date updateTime;

    /*所属用户*/
    private Long userId;

    private static final long serialVersionUID = 1L;

}