package com.mall4j.cloud.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lhd
 * @date 2020/12/30
 */
@ApiModel(value= "用户注册信息")
@Data
public class UserRegisterDTO {

	@NotBlank
	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@NotBlank
	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "用户id,不用管")
	private Long userId;

	@ApiModelProperty("手机号")
	private String accountPhone;

	@ApiModelProperty("uid")
	private String tempUid;

	@ApiModelProperty("验证码")
	private String code;
}
