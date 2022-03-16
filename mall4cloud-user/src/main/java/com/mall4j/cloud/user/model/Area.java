package com.mall4j.cloud.user.model;

import com.mall4j.cloud.common.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 省市区地区信息
 *
 * @author YXF
 * @date 2020-11-25 15:16:14
 */
@Data
public class Area extends BaseModel implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long areaId;

    /**
     * 地址
     */
    private String areaName;

    /**
     * 上级地址
     */
    private Long parentId;
	private String pcode;
	private String code;

    /**
     * 等级（从1开始）
     */
    private Integer level;


}
