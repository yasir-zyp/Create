package com.mall4j.cloud.order.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户订单配送地址VO
 *
 * @author FrozenWatermelon
 * @date 2020-12-05 14:13:50
 */
@Data
public class OrderAddrVO extends BaseVO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long orderAddrId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("省ID")
    private Long provinceId;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("城市ID")
    private Long cityId;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区域ID")
    private Long areaId;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("地址")
    private String addr;

    @ApiModelProperty("邮编")
    private String postCode;

    @ApiModelProperty("手机")
    private String mobile;
}
