package com.mall4j.cloud.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ConsultInfoVO{

    @ApiModelProperty("主键")
    private Long consultId;


    @ApiModelProperty("需求描叙")
    private String consultDesc;


    @ApiModelProperty("样品图片")
    private String consultImg;


   @ApiModelProperty("联系人")
    private String contactName;


    @ApiModelProperty("手机号")
    private String contactPhone;


    @ApiModelProperty("状态0提交，1已推荐")
    private Integer status;


    @ApiModelProperty("推荐服务方案")
    private String spuids;


    private static final long serialVersionUID = 1L;
}