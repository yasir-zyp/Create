package com.mall4j.cloud.multishop.model;

import com.mall4j.cloud.common.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ShopQualification extends BaseModel implements Serializable {
    /**
     * 资质信息id
     */
    private  Long shopQualificationId;

    /*
    * 资质证书名称
    * */
    private String nameQualification;

    /*
    * 资质证书图片地址
    * */
    private String qualificationUrl;

    /*
    * 资质状态
    * */
    private Integer status;

    /*
    * 资质外键
    * */
    private Long shopId;
}
