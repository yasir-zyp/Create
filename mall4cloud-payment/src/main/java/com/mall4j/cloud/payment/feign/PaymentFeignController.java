package com.mall4j.cloud.payment.feign;

import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.api.payment.bo.PayInfoBo;
import com.mall4j.cloud.api.payment.dto.PayInfoDto;
import com.mall4j.cloud.api.payment.feign.PaymentFeignClient;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.payment.bo.PayInfoBO;
import com.mall4j.cloud.payment.dto.PayInfoDTO;
import com.mall4j.cloud.payment.service.PayInfoService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class PaymentFeignController implements PaymentFeignClient {
    @Autowired
    private PayInfoService payInfoService;
    @Autowired
    private MapperFacade mapperFacade;
    @Override
    public ServerResponseEntity<PayInfoBo> pay(HttpServletRequest request, @Valid PayInfoDto payParam) {
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        Long userId = userInfoInTokenBO.getUserId();
        String openId=userInfoInTokenBO.getUnionId();
        PayInfoDTO payInfoDto=new PayInfoDTO();
        payInfoDto.setOrderIds(payParam.getOrderIds());
        PayInfoBO payInfo = payInfoService.pay(request,userId, payInfoDto,openId);
        payInfo.setBizUserId(userInfoInTokenBO.getBizUserId());
        PayInfoBo payInfoBo=mapperFacade.map(payInfo, PayInfoBo.class);
        return ServerResponseEntity.success(payInfoBo);
    }
}
