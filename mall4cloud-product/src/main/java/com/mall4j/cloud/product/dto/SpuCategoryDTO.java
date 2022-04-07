package com.mall4j.cloud.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpuCategoryDTO {
    @ApiModelProperty("id,主键")
    private Long spuCategoryId;

    private static final long serialVersionUID = 1L;
}