package com.mall4j.cloud.product.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShopAddr implements Serializable {
    /*主键*/
    private Long shopAddrId;
    /*详细地址*/
    private String theAddr;
    /*区*/
    private String areaName;
    /*区id*/
    private Long areaIds;
    /*市*/
    private String cityName;
    /*市id*/
    private Long cityId;
    /*省*/
    private String provinceName;
    /*省id*/
    private Long provinceId;
    /*收货人*/
    private String consignee;
    /*创建时间*/
    private Date createTime;
   /* 是否默认地址 1是,0否*/
    private Integer isDefault;

    private BigDecimal theLat;

    private BigDecimal theLng;
    /*手机*/
    private String mobilePhone;
    /*邮编*/
    private String postCode;
    /*修改时间*/
    private Date updateTime;
    /*商家id*/
    private Long shopId;

    private static final long serialVersionUID = 1L;
}