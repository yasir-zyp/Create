package com.mall4j.cloud.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用于登陆传递账号密码
 *
 * @author FrozenWatermelon
 * @date 2020/7/1
 */
@Data
public class AuthenticationDTO {

	/**
	 * 用户名
	 */
	@NotBlank(message = "principal不能为空")
	@ApiModelProperty(value = "用户名",required = true)
	protected String principal;

	/**
	 * 密码
	 */
	@NotBlank(message = "credentials不能为空")
	@ApiModelProperty(value = "一般用作密码", required = true)
	protected String credentials;

	/**
	 * sysType 参考SysTypeEnum
	 */
	@NotNull(message = "sysType不能为空")
	@ApiModelProperty(value = "系统类型 0.普通用户系统 1.商家端,2平台管理员", required = true)
	protected Integer sysType;

	@ApiModelProperty(value = "微信绑定唯一id", required = false)
	protected String unionId;
	@ApiModelProperty(value = "JSCode", required = false)
	protected String JSCode;

}
