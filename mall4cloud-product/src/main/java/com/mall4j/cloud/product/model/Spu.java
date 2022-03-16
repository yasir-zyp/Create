package com.mall4j.cloud.product.model;

import java.io.Serializable;

import com.mall4j.cloud.common.model.BaseModel;
import lombok.Data;

/**
 * spu信息
 *
 * @author FrozenWatermelon
 * @date 2020-12-08 15:54:23
 */
@Data
public class Spu extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * spu id
     */
    private Long spuId;

    /**
     * 分类ID
     */
    private Long categoryId;


    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * spu名称
     */
    private String name;

    /**
     * 服务方案产品名称
     */
    private String sellingPoint;
    /**
     * 商品编码
     */
    private String partyCode;
    /**
     * 检测周期(工作日)
     */
    private Integer cycle;
    /**
     * 服务方案类型0:样品检测，1现场检测
     */
    private Integer serviceType;

    /**
     * 主图
     */
    private String mainImgUrl;
    /**
     * 送样要求
     */
    private String sampleRequest;

    /**
     * 送样地址id
     */
    private Long addrId;
    /**
     * 售价，整数方式保存
     */
    private Long priceFee;

    /**
     * 市场价，整数方式保存
     */
    private Long marketPriceFee;
    /**
     * 热搜类型0:不需要，1:需要
     */
    private Integer searchType;

    /**

    /**
     * 状态 1:enable, 0:disable, -1:deleted
     */
    private Integer status;
    /**
     * 服务次数
     */
    private Integer saleNum;

    /**
     * 序号
     */
    private Integer seq;
}
