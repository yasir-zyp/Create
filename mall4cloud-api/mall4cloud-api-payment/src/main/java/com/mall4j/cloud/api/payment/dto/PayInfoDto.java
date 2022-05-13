package com.mall4j.cloud.api.payment.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PayInfoDto {
    @NotEmpty(message = "订单号不能为空")
    @ApiModelProperty(value = "订单号", required = true)
    private List<Long> orderIds;
}
