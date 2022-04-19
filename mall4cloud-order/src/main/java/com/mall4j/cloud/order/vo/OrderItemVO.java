package com.mall4j.cloud.order.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 订单项VO
 *
 * @author FrozenWatermelon
 * @date 2020-12-04 11:27:35
 */
@Data
public class OrderItemVO extends BaseVO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单项ID,主id")
    private Long orderItemId;

    @ApiModelProperty("店铺id")
    private Long shopId;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("产品ID")
    private Long spuId;

    @ApiModelProperty("产品SkuID")
    private Long skuId;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("购物车服务方案个数")
    private Integer count;

    @ApiModelProperty("服务方案名称")
    private String spuName;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("产品主图片路径")
    private String pic;

    @ApiModelProperty("单个orderItem的配送类型 3：无需快递")
    private Integer deliveryType;

    @ApiModelProperty("加入购物车时间")
    private Date shopCartTime;

    @ApiModelProperty("服务方案价格")
    private Long price;

    @ApiModelProperty("服务方案总金额")
    private Long spuTotalAmount;

	@ApiModelProperty("发货改变的数量")
	private Integer changeNum;

}
