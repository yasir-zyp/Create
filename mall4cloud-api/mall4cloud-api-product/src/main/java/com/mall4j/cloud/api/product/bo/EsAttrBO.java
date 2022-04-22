package com.mall4j.cloud.api.product.bo;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author FrozenWatermelon
 * @date 2020/11/12
 */
@Data
public class EsAttrBO {

    /**
     * 规格id
     */
    private Long attrId;

    /**
     * 检测项名称
     */
    private String attrName;


    /**
     * 标准号
     */
    private String standard;
    /**
     * 标准名称
     */
    private String standardName;
    /**
     * 检测资质
     */
    private Integer attrType;
    /**
     * 检测周期
     */
    private Integer cycle;

}
