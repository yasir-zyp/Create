package com.mall4j.cloud.payment.controller;


import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.common.util.BooleanUtil;
import com.mall4j.cloud.payment.bo.PayInfoBO;
import com.mall4j.cloud.payment.dto.PayInfoDTO;
import com.mall4j.cloud.payment.service.PayInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author FrozenWatermelon
 */
@RestController
@RequestMapping("/pay")
@Api(tags = "订单接口")
public class PayController {

    @Autowired
    private PayInfoService payInfoService;

    @Autowired
    private PayNoticeController payNoticeController;


    /**
     * 支付接口
     */
    @PostMapping("/order")
    @ApiOperation(value = "根据订单号进行支付", notes = "根据订单号进行支付")
    public ServerResponseEntity<?> pay(HttpServletRequest request, @Valid @RequestBody PayInfoDTO payParam) {
        // 这里的地址是网关通过转发过来的时候，获取到当前服务器的地址，测试环境要用测试环境的uri
        String gatewayUri = "http://192.168.11.120/mall4cloud_payment";
        UserInfoInTokenBO userInfoInTokenBO = AuthUserContext.get();
        Long userId = userInfoInTokenBO.getUserId();
        String openId=userInfoInTokenBO.getUnionId();
        PayInfoBO payInfo = payInfoService.pay(request,userId, payParam,openId);
        payInfo.setBizUserId(userInfoInTokenBO.getBizUserId());
        return ServerResponseEntity.success(payInfo);
    }

    @GetMapping("/isPay/{orderIds}")
    @ApiOperation(value = "根据订单号查询该订单是否已经支付", notes = "根据订单号查询该订单是否已经支付")
    public ResponseEntity<Boolean> isPay(@PathVariable String orderIds) {
        Long userId = AuthUserContext.get().getUserId();
        payInfoService.getPayStatusByOrderIds(orderIds);
        Integer isPay = payInfoService.isPay(orderIds, userId);
        return ResponseEntity.ok(BooleanUtil.isTrue(isPay));
    }
}
