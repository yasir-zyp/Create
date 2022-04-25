package com.mall4j.cloud.user.model;

import com.mall4j.cloud.common.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户地址
 *
 * @author FrozenWatermelon
 * @date 2020-12-07 15:50:02
 */
@Data
public class UserAddr extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;
	public static final Integer DEFAULT_ADDR = 1;
	public static final Integer NOT_DEFAULT_ADDR = 0;

    /**
     * ID
     */
    private Long addrId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 是否默认地址 1是
     */
    private Integer isDefault;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 省ID
     */
    private Long provinceId;

    /**
     * 省
     */
    private String province;

    /**
     * 城市ID
     */
    private Long cityId;

    /**
     * 城市
     */
    private String city;

    /**
     * 区ID
     */
    private Long areaId;

    /**
     * 区
     */
    private String area;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 地址
     */
    private String addr;

    /**
     * 经度
     */
    private Double lng;

    /**
     * 纬度
     */
    private Double lat;


	private Integer areas[];
}
