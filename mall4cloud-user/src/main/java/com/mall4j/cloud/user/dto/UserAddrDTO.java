package com.mall4j.cloud.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用户地址DTO
 *
 * @author FrozenWatermelon
 * @date 2020-12-07 15:50:02
 */
@Data
public class UserAddrDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long addrId;

    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("是否默认地址 1是")
    private Integer isDefault;

    @ApiModelProperty("收货人")
	@Length(min = 2, max = 20, message = "收货人姓名需要在2到20个字符之间")
    private String consignee;

    @ApiModelProperty("省ID")
    private Long provinceId;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("城市ID")
    private Long cityId;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区ID")
    private Long areaId;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("邮编")
    private String postCode;

    @ApiModelProperty("地址")
    private String addr;

    @ApiModelProperty("经度")
    private Double lng;

    @ApiModelProperty("纬度")
    private Double lat;

    @ApiModelProperty("地区")
    private Integer areas[];
}
