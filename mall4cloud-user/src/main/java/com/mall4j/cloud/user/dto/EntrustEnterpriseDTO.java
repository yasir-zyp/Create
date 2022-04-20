package com.mall4j.cloud.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class EntrustEnterpriseDTO{
    @ApiModelProperty("主键")
    private Long entrustEnterpriseId;

    @ApiModelProperty("营业执照")
    private String businessLicense;

    @ApiModelProperty("组织机构代码")
    private String entrustNumber;

    @ApiModelProperty("法人")
    private String entrustLegal;

    @ApiModelProperty("地址")
    private String entrustAddress;

    @ApiModelProperty("开户行")
    private String entrustBankDeposit;

    @ApiModelProperty("银行账号")
    private String entrustBankAccount;

    @ApiModelProperty("企业名称")
    private String enterpriseName;

    @ApiModelProperty("认证状态1正常，0审核中，只回显")
    private Integer enterperseStatus;

    private static final long serialVersionUID = 1L;

}