package com.mall4j.cloud.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SpuCategoryVO {
    @ApiModelProperty("id,主键")
    private Long spuCategoryId;

    @ApiModelProperty("分类标签")
    private String tagsDescribe;

    private static final long serialVersionUID = 1L;
}