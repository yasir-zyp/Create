package com.mall4j.cloud.api.user.vo;

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 省市区地区信息VO
 *
 * @author YXF
 * @date 2020-11-25 15:16:14
 */
@Data
public class AreaVO extends BaseVO{
    private static final long serialVersionUID = 1L;

	@ApiModelProperty()
	private Long areaId;

	@ApiModelProperty("地址")
	private String areaName;
	@ApiModelProperty("父地址")
	private String pcode;
	@ApiModelProperty("子地址")
	private String code;

	@ApiModelProperty("上级地址")
	private Long parentId;

	@ApiModelProperty("等级（从1开始）")
	private Integer level;

	private Integer check;

	/**
	 * 下级地址集合
	 */
	private List<AreaVO> areas;

	/**
	 * 下级地址的areaId
	 */
	private List<Long> areaIds;

}
