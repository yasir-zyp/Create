package com.mall4j.cloud.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InvoiceInfoDTO{

    @ApiModelProperty("主键")
    private Long invoiceId;


    @ApiModelProperty("是否需要发票")
    private Byte needInvoice;


    @ApiModelProperty("发票抬头")
    private String invoiceLooked;


    @ApiModelProperty("纳税人识别号")
    private String taxpayerIdentificationNumber;


    @ApiModelProperty("开票类型")
    private String invoiceMakeType;


    @ApiModelProperty("注册地址")
    private String invoiceRegisteredAddress;


    @ApiModelProperty("开户行")
    private String invoiceOpeningBank;


    @ApiModelProperty("银行账号")
    private String invoiceBankAccount;


    @ApiModelProperty("注册电话")
    private String invoiceRegisteredPhone;


    @ApiModelProperty("发票邮寄地址")
    private String invoiceMailingAddress;
    private static final long serialVersionUID = 1L;

}