package com.mall4j.cloud.api.product.bo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author FrozenWatermelon
 * @date 2020/11/12
 */
@Data
public class EsProductBO {

    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 服务方案编号
     */
    private String partyCode;
    /**
     * 服务方案名称
     */
    private String spuName;
    /**
     * 店铺id
     */
    private Long shopId;
    /**
     * 分类id
     */
    private Long categoryId;
    /**
     * 送样地址id
     */

    private Long addrId;
    /**
     * 检测产品名称
     */
    private String sellingPoint;

    /**
     * 商品售价
     */
    private Long priceFee;

    /**
     * 市场价，整数方式保存
     */
    private Long marketPriceFee;

    /**
     * 商品介绍主图
     */
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String mainImgUrl;

    /**
     * 店铺名称 搜索华为的时候，可以把华为的旗舰店搜索出来
     */
    private String shopName;



    /**
     * 店铺logo
     */
    private String shopImg;

    /**
     * 店铺类型1自营店 2普通店
     */
    private Integer shopType;

    /**
     * 商品状态
     */
    private Integer spuStatus;
    /**
     * 送样要求
     */
    private String sample_Request;


    /**
     * 销量
     */
    private Integer saleNum;

    /**
     * 服务方案创建时间
     */
    private Date createTime;


    /**
     * 商品序号
     */
    private Integer seq;



    /**
     * 分类名称
     */
    private String categoryName;
    /**
     *检测周期
     */
    private Integer cycle;
    /**
     * 热门搜索0不需要，1需要
     */
    private Integer searchType;

    /**
     * 平台一级分类id
     */
    private Long primaryCategoryId;


    /**
     * 平台一级分类名称
     */
    private String primaryCategoryName;

    /**
     * 平台二级分类id
     */
    private Long secondaryCategoryId;

    /**
     * 平台二级分类名称
     */
    private String secondaryCategoryName;

    /**
     * 商品用于搜索的规格属性
     */
    private List<EsAttrBO> attrs;

}
