package com.mall4j.cloud.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mall4j.cloud.api.auth.dto.AuthAccountDTO;
import com.mall4j.cloud.auth.constant.AuthAccountStatusEnum;
import com.mall4j.cloud.auth.dto.AuthenticationDTO;
import com.mall4j.cloud.auth.dto.PhoneMessageDTO;
import com.mall4j.cloud.auth.dto.SmsMessageDTO;
import com.mall4j.cloud.auth.model.AuthAccount;
import com.mall4j.cloud.common.cache.util.RedisUtil;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.security.bo.AuthAccountInVerifyBO;
import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.common.security.constant.InputUserNameEnum;
import com.mall4j.cloud.auth.mapper.AuthAccountMapper;
import com.mall4j.cloud.auth.service.AuthAccountService;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.util.PrincipalUtil;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author FrozenWatermelon
 * @date 2020/7/2
 */
@Service
public class AuthAccountServiceImpl implements AuthAccountService {

	@Resource
	private AuthAccountMapper authAccountMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MapperFacade mapperFacade;


	public static final String USER_NOT_FOUND_SECRET = "USER_NOT_FOUND_SECRET";

	private static String userNotFoundEncodedPassword;

	@Override
	public ServerResponseEntity<UserInfoInTokenBO> getUserInfoInTokenByInputUserNameAndPassword(String inputUserName,
			String password, Integer sysType) {

		if (StrUtil.isBlank(inputUserName)) {
			return ServerResponseEntity.showFailMsg("用户名不能为空");
		}
		if (StrUtil.isBlank(password)) {
			return ServerResponseEntity.showFailMsg("密码不能为空");
		}

		InputUserNameEnum inputUserNameEnum = null;

		// 用户名
		if (PrincipalUtil.isUserName(inputUserName)) {
			inputUserNameEnum = InputUserNameEnum.USERNAME;
		}

		if (inputUserNameEnum == null) {
			return ServerResponseEntity.showFailMsg("请输入正确的用户名");
		}

		AuthAccountInVerifyBO authAccountInVerifyBO = authAccountMapper
				.getAuthAccountInVerifyByInputUserName(inputUserNameEnum.value(), inputUserName, sysType);

		if (authAccountInVerifyBO == null) {
			prepareTimingAttackProtection();
			// 再次进行运算，防止计时攻击
			// 计时攻击（Timing
			// attack），通过设备运算的用时来推断出所使用的运算操作，或者通过对比运算的时间推定数据位于哪个存储设备，或者利用通信的时间差进行数据窃取。
			mitigateAgainstTimingAttack(password);
			return ServerResponseEntity.showFailMsg("用户名或密码不正确");
		}

		if (Objects.equals(authAccountInVerifyBO.getStatus(), AuthAccountStatusEnum.DISABLE.value())) {
			return ServerResponseEntity.showFailMsg("用户已禁用，请联系客服");
		}
		String password1=passwordEncoder.encode(password);
		if (!passwordEncoder.matches(password, authAccountInVerifyBO.getPassword())) {
			return ServerResponseEntity.showFailMsg("用户名或密码不正确");
		}

		return ServerResponseEntity.success(mapperFacade.map(authAccountInVerifyBO, UserInfoInTokenBO.class));
	}

	@Override
	public ServerResponseEntity<UserInfoInTokenBO> getUserInfoInTokenByUnionId(String unionId) {
		if (StrUtil.isBlank(unionId)) {
			return ServerResponseEntity.showFailMsg("小程序登录失败");
		}
		InputUserNameEnum inputUserNameEnum = InputUserNameEnum.unionId;
		AuthAccountInVerifyBO authAccountInVerifyBO = authAccountMapper
				.getAuthAccountInVerifyByInputUserName(inputUserNameEnum.value(), unionId, 0);
		if (authAccountInVerifyBO == null) {
			prepareTimingAttackProtection();
			// 再次进行运算，防止计时攻击
			// 计时攻击（Timing
			// attack），通过设备运算的用时来推断出所使用的运算操作，或者通过对比运算的时间推定数据位于哪个存储设备，或者利用通信的时间差进行数据窃取。
			mitigateAgainstTimingAttack(unionId);
			UserInfoInTokenBO userInfoInTokenBO=new UserInfoInTokenBO();
            ServerResponseEntity<UserInfoInTokenBO> serverResponseEntity=ServerResponseEntity.fail(ResponseEnum.SOCIAL_ACCOUNT_NOT_BIND,userInfoInTokenBO);
            serverResponseEntity.setMsg(unionId);
			return serverResponseEntity;
		}
		if (Objects.equals(authAccountInVerifyBO.getStatus(), AuthAccountStatusEnum.DISABLE.value())) {
			return ServerResponseEntity.showFailMsg("用户已禁用，请联系客服");
		}
		return ServerResponseEntity.success(mapperFacade.map(authAccountInVerifyBO, UserInfoInTokenBO.class));
	}

	@Override
    public AuthAccount getByUserIdAndType(Long userId, Integer sysType) {
        return authAccountMapper.getByUserIdAndType(userId, sysType);
    }

	@Override
	public void updatePassword(Long userId, Integer sysType, String newPassWord) {
		authAccountMapper.updatePassword(userId, sysType, passwordEncoder.encode(newPassWord));
	}

	@Override
	public AuthAccount getByUid(Long uid) {
		return authAccountMapper.getByUid(uid);
	}

    @Override
    public AuthAccount getAccountByInputUserName(String mobile, Integer systemType) {
        return authAccountMapper.getAccountByInputUserName(mobile,systemType);
    }

	@Override
	public AuthAccount findPhone(String accountPhone,Integer sysType) {
		return authAccountMapper.findPhone(accountPhone,sysType);
	}

	@Override
	public ServerResponseEntity<UserInfoInTokenBO> getUserTokenByPhone(PhoneMessageDTO phoneMessageDTO) {
		if (StrUtil.isBlank(phoneMessageDTO.getAccountPhone())) {
			return ServerResponseEntity.showFailMsg("电话号码不能为空");
		}
		if (StrUtil.isBlank(phoneMessageDTO.getCode())) {
			return ServerResponseEntity.showFailMsg("验证码不能为空");
		}
		//判断验证码是否输入正确
		String codes= RedisUtil.getLongValues(phoneMessageDTO.getAccountPhone());
		if (codes==null){
			return ServerResponseEntity.showFailMsg("未发送验证码");
		}
		if (!codes.equals(phoneMessageDTO.getCode())){
			return ServerResponseEntity.showFailMsg("验证码不正确");
		}
		/*
		*根据输入的电话号码及用户名类型获取用户信息
		* */
		InputUserNameEnum inputUserNameEnum = null;

		// 手机号
		if (PrincipalUtil.isMobile(phoneMessageDTO.getAccountPhone())) {
			inputUserNameEnum = InputUserNameEnum.PHONE;
		}


		AuthAccountInVerifyBO authAccountInVerifyBO = authAccountMapper
				.getAuthAccountInVerifyByInputUserName(inputUserNameEnum.value(),phoneMessageDTO.getAccountPhone(),phoneMessageDTO.getSysType());
		if (authAccountInVerifyBO == null) {
			return ServerResponseEntity.showFailMsg("该用户不存在");
		}
		if (Objects.equals(authAccountInVerifyBO.getStatus(), AuthAccountStatusEnum.DISABLE.value())) {
			return ServerResponseEntity.showFailMsg("用户已禁用，请联系客服");
		}
		return ServerResponseEntity.success(mapperFacade.map(authAccountInVerifyBO, UserInfoInTokenBO.class));
	}

	@Override
	public ServerResponseEntity<UserInfoInTokenBO> bindUnionId(AuthenticationDTO authenticationDTO) {
		if (StrUtil.isBlank(authenticationDTO.getPrincipal())) {
			return ServerResponseEntity.showFailMsg("电话号码未获取成功");
		}
		/*
		 *根据输入的电话号码及用户名类型获取用户信息
		 * */
		InputUserNameEnum inputUserNameEnum = InputUserNameEnum.PHONE;
		Integer sysType=0;

		AuthAccountInVerifyBO authAccountInVerifyBO = authAccountMapper
				.getAuthAccountInVerifyByInputUserName(inputUserNameEnum.value(),authenticationDTO.getPrincipal(),sysType);
		if (authAccountInVerifyBO == null) {
			prepareTimingAttackProtection();
			// 再次进行运算，防止计时攻击
			// 计时攻击（Timing
			// attack），通过设备运算的用时来推断出所使用的运算操作，或者通过对比运算的时间推定数据位于哪个存储设备，或者利用通信的时间差进行数据窃取。
			mitigateAgainstTimingAttack(authenticationDTO.getPrincipal());
			UserInfoInTokenBO userInfoInTokenBO=new UserInfoInTokenBO();
			ServerResponseEntity<UserInfoInTokenBO> serverResponseEntity=ServerResponseEntity.fail(ResponseEnum.ACCOUNT_NOT_REGISTER,userInfoInTokenBO);
			serverResponseEntity.setMsg(authenticationDTO.getPrincipal());
			return serverResponseEntity;
		}
		if (Objects.equals(authAccountInVerifyBO.getStatus(), AuthAccountStatusEnum.DISABLE.value())) {
			return ServerResponseEntity.showFailMsg("用户已禁用，请联系客服");
		}
		return ServerResponseEntity.success(mapperFacade.map(authAccountInVerifyBO, UserInfoInTokenBO.class));

	}


	/**
	 * 防止计时攻击
	 */
	private void prepareTimingAttackProtection() {
		if (userNotFoundEncodedPassword == null) {
			userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_SECRET);
		}
	}

	/**
	 * 防止计时攻击
	 */
	private void mitigateAgainstTimingAttack(String presentedPassword) {
		if (presentedPassword != null) {
			this.passwordEncoder.matches(presentedPassword, userNotFoundEncodedPassword);
		}
	}

}
