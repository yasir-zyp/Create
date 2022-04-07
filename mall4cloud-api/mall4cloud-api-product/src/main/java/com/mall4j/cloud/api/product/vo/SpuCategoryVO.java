package com.mall4j.cloud.api.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpuCategoryVO {
    @ApiModelProperty("id,主键不显示")
    private Long spuCategoryId;

    @ApiModelProperty("分类标签")
    private String tagsDescribe;

    private static final long serialVersionUID = 1L;
}