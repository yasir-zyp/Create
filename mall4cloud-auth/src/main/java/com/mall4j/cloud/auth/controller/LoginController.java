package com.mall4j.cloud.auth.controller;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.api.auth.dto.AuthAccountDTO;
import com.mall4j.cloud.api.auth.feign.AccountFeignClient;
import com.mall4j.cloud.api.rbac.dto.ClearUserPermissionsCacheDTO;
import com.mall4j.cloud.api.rbac.feign.PermissionFeignClient;
import com.mall4j.cloud.auth.dto.AuthenticationDTO;
import com.mall4j.cloud.auth.dto.PhoneMessageDTO;
import com.mall4j.cloud.auth.dto.SmsMessageDTO;
import com.mall4j.cloud.auth.manager.TokenStore;
import com.mall4j.cloud.auth.model.AuthAccount;
import com.mall4j.cloud.auth.service.AuthAccountService;
import com.mall4j.cloud.auth.service.impl.SendMessageService;
import com.mall4j.cloud.common.cache.util.RedisUtil;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.api.auth.vo.TokenInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author FrozenWatermelon
 * @date 2020/6/30
 */
@RestController
@Api(tags = "登录")
public class LoginController {

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private AuthAccountService authAccountService;

	@Autowired
	private PermissionFeignClient permissionFeignClient;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AccountFeignClient accountFeignClient;

	@Autowired
	SendMessageService sendMessageService;
	@PostMapping("/ua/login")
	@ApiOperation(value = "账号密码", notes = "通过账号登录，还要携带用户的类型，也就是用户所在的系统")
	public ServerResponseEntity<TokenInfoVO> login(
			@Valid @RequestBody AuthenticationDTO authenticationDTO) {

		// 这边获取了用户的用户信息，那么根据sessionid对应一个user的原则，我应该要把这个东西存起来，然后校验，那么存到哪里呢？
		// redis，redis有天然的自动过期的机制，有key value的形式
		ServerResponseEntity<UserInfoInTokenBO> userInfoInTokenResponse = authAccountService
				.getUserInfoInTokenByInputUserNameAndPassword(authenticationDTO.getPrincipal(),
						authenticationDTO.getCredentials(), authenticationDTO.getSysType());


		if (!userInfoInTokenResponse.isSuccess()) {
			return ServerResponseEntity.transform(userInfoInTokenResponse);
		}

		UserInfoInTokenBO data = userInfoInTokenResponse.getData();

		ClearUserPermissionsCacheDTO clearUserPermissionsCacheDTO = new ClearUserPermissionsCacheDTO();
		clearUserPermissionsCacheDTO.setSysType(data.getSysType());
		clearUserPermissionsCacheDTO.setUserId(data.getUserId());
		// 将以前的权限清理了,以免权限有缓存
		ServerResponseEntity<Void> clearResponseEntity = permissionFeignClient.clearUserPermissionsCache(clearUserPermissionsCacheDTO);

		if (!clearResponseEntity.isSuccess()) {
			return ServerResponseEntity.fail(ResponseEnum.UNAUTHORIZED);
		}

		// 保存token，返回token数据给前端，这里是最重要的
		return ServerResponseEntity.success(tokenStore.storeAndGetVo(data));
	}
	@PostMapping("/ua/login_phone")
	@ApiOperation(value = "手机号登录", notes = "通过手机号验证码的形式直接登录")
	public ServerResponseEntity<TokenInfoVO> loginPhone(@Valid @RequestBody PhoneMessageDTO phoneMessageDTO) {
		ServerResponseEntity<UserInfoInTokenBO> userInfoInTokenResponse = authAccountService.getUserTokenByPhone(phoneMessageDTO);
		if (!userInfoInTokenResponse.isSuccess()) {
			return ServerResponseEntity.transform(userInfoInTokenResponse);
		}

		UserInfoInTokenBO data = userInfoInTokenResponse.getData();

		ClearUserPermissionsCacheDTO clearUserPermissionsCacheDTO = new ClearUserPermissionsCacheDTO();
		clearUserPermissionsCacheDTO.setSysType(data.getSysType());
		clearUserPermissionsCacheDTO.setUserId(data.getUserId());
		// 将以前的权限清理了,以免权限有缓存
		ServerResponseEntity<Void> clearResponseEntity = permissionFeignClient.clearUserPermissionsCache(clearUserPermissionsCacheDTO);
		if (!clearResponseEntity.isSuccess()) {
			return ServerResponseEntity.fail(ResponseEnum.UNAUTHORIZED);
		}
		/*清除缓存*/
		RedisUtil.del(phoneMessageDTO.getAccountPhone());
		// 保存token，返回token数据给前端，这里是最重要的
		return ServerResponseEntity.success(tokenStore.storeAndGetVo(data));
	}

	@PostMapping("/login_out")
	@ApiOperation(value = "退出登陆", notes = "点击退出登陆，清除token，清除菜单缓存")
	public ServerResponseEntity<TokenInfoVO> loginOut() {
		UserInfoInTokenBO userInfoInToken = AuthUserContext.get();
		ClearUserPermissionsCacheDTO clearUserPermissionsCacheDTO = new ClearUserPermissionsCacheDTO();
		clearUserPermissionsCacheDTO.setSysType(userInfoInToken.getSysType());
		clearUserPermissionsCacheDTO.setUserId(userInfoInToken.getUserId());
		// 将以前的权限清理了,以免权限有缓存
		permissionFeignClient.clearUserPermissionsCache(clearUserPermissionsCacheDTO);
		// 删除该用户在该系统的token
		tokenStore.deleteAllToken(userInfoInToken.getSysType().toString(), userInfoInToken.getUid());
		return ServerResponseEntity.success();
	}
	@PostMapping("/ua/checkCode")
	@ApiOperation(value = "发送短信", notes = "发送短信")
	public ServerResponseEntity<Object> code(@RequestBody SmsMessageDTO smsMessageDTO) throws Exception {
		//生成四位码
		int a;
		// a=(int)(Math.random()*899999+100000);//六位验证码
		a = (int) ((Math.random() * 9 + 1) * 1000);// 4位验证码

		String code = String.valueOf(a);

		//查询是否存在手机号
		AuthAccount authAccount=authAccountService.findPhone(smsMessageDTO.getAccountPhone());
		if (authAccount!=null){
			return ServerResponseEntity.showFailMsg("手机号已存在");
		}
		RedisUtil.set(smsMessageDTO.getAccountPhone(),a,1200);
		sendMessageService.login(smsMessageDTO.getAccountPhone(), code);
		Map<String,Object> map=new HashMap<>();
		map.put("msg","验证码已发送");
		return ServerResponseEntity.success(map);
	}
	@PostMapping("/ua/Code_phone_login")
	@ApiOperation(value = "用户手机登录发送短信", notes = "发送短信")
	public ServerResponseEntity<Object> CodePhoneLogin(@RequestBody SmsMessageDTO smsMessageDTO) throws Exception {
		//生成四位码
		int a;
		// a=(int)(Math.random()*899999+100000);//六位验证码
		a = (int) ((Math.random() * 9 + 1) * 1000);// 4位验证码

		String code = String.valueOf(a);
		RedisUtil.set(smsMessageDTO.getAccountPhone(),a,1200);
		sendMessageService.login(smsMessageDTO.getAccountPhone(), code);
		Map<String,Object> map=new HashMap<>();
		map.put("msg","验证码已发送");
		return ServerResponseEntity.success(map);
	}
}
