package com.mall4j.cloud.multishop.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ShopQualification implements Serializable {
    private Long shopQualificationId;

    private String nameQualification;

    private String qualificationUrl;

    private Date createTime;

    private Date upadteTime;

    private Integer status;

    private Long shopId;

    private static final long serialVersionUID = 1L;

}