package com.mall4j.cloud.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @author FrozenWatermelon
 */
@Data
public class OrderSearchDTO {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 店铺id
     */
    private Long shopId;

    @ApiModelProperty("订单状态 1:待付款 2:待发货 3:待收货(已发货) 5:成功 6:失败")
    private Integer status;

    @ApiModelProperty("是否已经支付，1：已经支付过，0：没有支付过")
    private Integer isPayed;

    @ApiModelProperty("订单号,主id")
    private Long orderId;

    @ApiModelProperty("下单的时间范围:开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty("下单的时间范围:结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("商品名称")
    private String spuName;

    @ApiModelProperty("收货人姓名")
    private String consignee;

    @ApiModelProperty("收货人手机号")
    private String mobile;

    @ApiModelProperty("物流类型 3：无需快递")
    private Integer deliveryType;

    @ApiModelProperty("开始页")
    private Integer pageNum;

    @ApiModelProperty("每页大小")
    private Integer pageSize;

}
