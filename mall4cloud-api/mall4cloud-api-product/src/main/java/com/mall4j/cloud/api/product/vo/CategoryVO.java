package com.mall4j.cloud.api.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mall4j.cloud.common.serializer.ImgJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分类信息VO
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Data
public class CategoryVO{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("店铺id")
    private Long shopId;

    @ApiModelProperty("父ID")
    private Long parentId;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类描述")
    private String desc;

    @ApiModelProperty("分类地址{parent_id}-{child_id},...")
    private String path;

    @ApiModelProperty("状态 1:enable, 0:disable, -1:deleted")
    private Integer status;

	@JsonSerialize(using = ImgJsonSerializer.class)
    @ApiModelProperty("分类图标")
    private String icon;

	@JsonSerialize(using = ImgJsonSerializer.class)
    @ApiModelProperty("分类的显示图片")
    private String imgUrl;

    @ApiModelProperty("分类层级 从0开始")
    private Integer level;

	@ApiModelProperty("排序")
	private Integer seq;

	@ApiModelProperty("上级分类名称")
	private List<String> pathNames;

	@ApiModelProperty("子分类列表")
	private List<CategoryVO> categories;

}
