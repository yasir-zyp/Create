package com.mall4j.cloud.user.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ConsultInfo implements Serializable {
    /*主键*/
    private Integer consultId;

    /*需求描叙*/
    private String consultDesc;

    /*样品图片*/
    private String consultImg;

   /* 联系人*/
    private String contactName;

    /*手机号*/
    private String contactPhone;

    /*创建时间*/
    private Date createTime;

    /*更新时间*/
    private Date updateTime;

    /*所属用户*/
    private Long userId;

    /*状态0提交，1已推荐*/
    private Integer status;

    /*推荐服务方案*/
    private String spuids;

    /*撤回*/
    private Integer delFlag;


    private static final long serialVersionUID = 1L;
}