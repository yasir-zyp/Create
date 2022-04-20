package com.mall4j.cloud.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class EntrustEnterprise implements Serializable {
    private Long entrustEnterpriseId;

    private String businessLicense;

    private String entrustNumber;

    private String entrustLegal;

    private String entrustAddress;

    private String entrustBankDeposit;

    private String entrustBankAccount;

    private String enterpriseName;

    private Integer enterperseStatus;

    private Date createTime;

    private Date updateTime;

    private String delFlag;

    private Long userId;

    private static final long serialVersionUID = 1L;

}