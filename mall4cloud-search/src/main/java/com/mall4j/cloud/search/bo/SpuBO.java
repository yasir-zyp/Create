package com.mall4j.cloud.search.bo;

import cn.throwx.canal.gule.annotation.CanalModel;
import cn.throwx.canal.gule.common.FieldNamingPolicy;
import lombok.Data;

/**
 * 商品信息
 *
 * @author YXF
 * @date 2020-12-23 15:27:24
 */
@CanalModel(database = "mall4cloud_product", table = "spu", fieldNamingPolicy = FieldNamingPolicy.LOWER_UNDERSCORE)
@Data
public class SpuBO {
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
     * 送样地址id
     */
    private Long addrId;
    /**
     * spu名称
     */
    private String name;

    /**
     * 检测产品名称
     */
    private String sellingPoint;

    /**
     * 服务方案主图
     */
    private String mainImgUrl;


    /**
     * 检测类型0送样，1现场检测
     *
     */
    private  String serviceType;

    /**
     * 样品要求
     */
    private String sampleRequest;

    /**
     * 售价，整数方式保存
     */
    private Long priceFee;

    /**
     * 市场价，整数方式保存
     */
    private Long marketPriceFee;
    /**
     * 热门搜索
     */
    private Integer searchType;
    /**
     * 检测周期
     */
    private Integer cycle;
    /**
     * 服务次数
     */
    private Integer saleNum;
    /**
     * 服务方案编码
     */
    private  String partyCode;

    /**
     * 状态 1:enable, 0:disable, -1:deleted
     */
    private Integer status;

    private Integer hasSkuImg;

    /**
     * 序号
     */
    private Integer seq;

}
