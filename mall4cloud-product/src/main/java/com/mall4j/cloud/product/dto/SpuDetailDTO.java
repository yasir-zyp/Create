package com.mall4j.cloud.product.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品详情信息DTO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Data
public class SpuDetailDTO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("商品id")
    private Long spuId;

    @ApiModelProperty("商品详情")
    private String detail;

}
