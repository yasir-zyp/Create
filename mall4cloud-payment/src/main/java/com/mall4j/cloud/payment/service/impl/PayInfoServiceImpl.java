package com.mall4j.cloud.payment.service.impl;

import cn.hutool.core.lang.id.NanoId;
import cn.hutool.core.util.StrUtil;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.mall4j.cloud.api.leaf.feign.SegmentFeignClient;
import com.mall4j.cloud.api.order.feign.OrderFeignClient;
import com.mall4j.cloud.api.order.vo.OrderAmountVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.order.bo.PayNotifyBO;
import com.mall4j.cloud.common.response.ResponseEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.rocketmq.config.RocketMqConstant;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.payment.bo.PayInfoBO;
import com.mall4j.cloud.payment.bo.PayInfoResultBO;
import com.mall4j.cloud.payment.config.WxConfig;
import com.mall4j.cloud.payment.constant.PayStatus;
import com.mall4j.cloud.payment.dto.PayInfoDTO;
import com.mall4j.cloud.payment.mapper.PayInfoMapper;
import com.mall4j.cloud.payment.model.PayInfo;
import com.mall4j.cloud.payment.service.PayInfoService;
import com.mall4j.cloud.payment.util.DateDealwith;
import com.mall4j.cloud.payment.util.WXPayUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 订单支付记录
 *
 * @author FrozenWatermelon
 * @date 2020-12-25 09:50:59
 */
@Service
public class PayInfoServiceImpl implements PayInfoService {

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Autowired
    private SegmentFeignClient segmentFeignClient;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private RocketMQTemplate orderNotifyTemplate;
    @Autowired
    private WxPayService wxPayService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayInfoBO pay(HttpServletRequest request,Long userId, PayInfoDTO payParam,String openId) {
        // 支付单号
        ServerResponseEntity<Long> segmentIdResponse = segmentFeignClient.getSegmentId(PayInfo.DISTRIBUTED_ID_KEY);
        if (!segmentIdResponse.isSuccess()) {
            throw new Mall4cloudException(ResponseEnum.EXCEPTION);
        }
        Long payId = segmentIdResponse.getData();
        List<Long> orderIds = payParam.getOrderIds();

        ServerResponseEntity<OrderAmountVO> ordersAmountAndIfNoCancelResponse = orderFeignClient.getOrdersAmountAndIfNoCancel(orderIds);
        // 如果订单已经关闭了，此时不能够支付了
        if (!ordersAmountAndIfNoCancelResponse.isSuccess()) {
            throw new Mall4cloudException(ordersAmountAndIfNoCancelResponse.getMsg());
        }
        OrderAmountVO orderAmount = ordersAmountAndIfNoCancelResponse.getData();

        Integer fenMoney = orderAmount.getPayAmount().intValue();
        //外部订单号
        String bizPayNo= NanoId.randomNanoId(16);
        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest=new WxPayUnifiedOrderRequest();
        //签名类型
        wxPayUnifiedOrderRequest.setSignType(WxPayConstants.SignType.MD5);
        //wxPayUnifiedOrderRequest.setSignType("RSA");
        wxPayUnifiedOrderRequest.setBody("安徽国科检测科技有限公司-检测");
        //外部订单号
        wxPayUnifiedOrderRequest.setOutTradeNo(bizPayNo); //自己生成order_No
        //交易类型
        wxPayUnifiedOrderRequest.setTradeType(WxPayConstants.TradeType.JSAPI);
        //wxPayUnifiedOrderRequest.setTotalFee(fenMoney);//直接分
        wxPayUnifiedOrderRequest.setTotalFee(1);
        wxPayUnifiedOrderRequest.setOpenid(openId); // 获取微信支付用户的openId
        wxPayUnifiedOrderRequest.setSpbillCreateIp(DateDealwith.getIpAddress(request));
        wxPayUnifiedOrderRequest.setDeviceInfo("WEB");
        Date now = new Date();
        Date afterDate = new Date(now.getTime() + 600000); //10分钟后的时间
        wxPayUnifiedOrderRequest.setTimeStart(DateFormatUtils.format(now, "yyyyMMddHHmmss"));
        wxPayUnifiedOrderRequest.setTimeExpire(DateFormatUtils.format(afterDate, "yyyyMMddHHmmss"));
        WxPayUnifiedOrderResult wxPayUnifiedOrderResult=null;
        try {
            wxPayUnifiedOrderResult=  wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        PayInfo payInfo = new PayInfo();
        payInfo.setPayId(payId);
        payInfo.setUserId(userId);
        payInfo.setPayAmount(orderAmount.getPayAmount());
        payInfo.setPayStatus(PayStatus.UNPAY.value());
        payInfo.setSysType(0);
        payInfo.setVersion(0);
        payInfo.setBizPayNo(bizPayNo);
        // 保存多个支付订单号
        payInfo.setOrderIds(StrUtil.join(StrUtil.COMMA, orderIds));
        // 保存预支付信息
        payInfoMapper.save(payInfo);
        //3. 生成小程序调起支付所需的数据
        String NonceStr=WXPayUtil.generateNonceStr();
        PayInfoBO payInfoDto = new PayInfoBO();
        payInfoDto.setPayAmount(orderAmount.getPayAmount());
        payInfoDto.setPayId(payId);
        //时间戳
        String timeStamp=(System.currentTimeMillis()/1000)+"";
        payInfoDto.setTimeStamp(timeStamp);
        payInfoDto.setPrepay("prepay_id="+wxPayUnifiedOrderResult.getPrepayId());
        payInfoDto.setNonceStr(NonceStr);
        payInfoDto.setSignType("MD5");
        //payInfoDto.setSignType("RSA");
        Map<String, String> data=new HashMap<>();
        data.put("appId",wxPayUnifiedOrderResult.getAppid());
        data.put("timeStamp",timeStamp);
        data.put("nonceStr",NonceStr);
        data.put("package","prepay_id="+wxPayUnifiedOrderResult.getPrepayId());
        data.put("signType","MD5");
        //data.put("signType","RSA");
        try {
            payInfoDto.setSign(WXPayUtil.generateSignature(data,WxConfig.mchKey));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return payInfoDto;
    }

    @Override
    public PayInfo getByPayId(Long payId) {
        return payInfoMapper.getByPayId(payId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void paySuccess(PayInfoResultBO payInfoResult, List<Long> orderIds) {
        // 标记为支付成功状态
        PayInfo payInfo = new PayInfo();
        payInfo.setPayId(payInfoResult.getPayId());
        payInfo.setBizPayNo(payInfoResult.getBizPayNo());
        payInfo.setCallbackContent(payInfoResult.getCallbackContent());
        payInfo.setCallbackTime(new Date());
        payInfo.setPayStatus(PayStatus.PAYED.value());
        payInfoMapper.update(payInfo);
        // 发送消息，订单支付成功
        SendStatus sendStatus = orderNotifyTemplate.syncSend(RocketMqConstant.ORDER_NOTIFY_TOPIC, new GenericMessage<>(new PayNotifyBO(orderIds))).getSendStatus();
        if (!Objects.equals(sendStatus, SendStatus.SEND_OK)) {
            // 消息发不出去就抛异常，因为订单回调会有多次，几乎不可能每次都无法发送出去，发的出去无所谓因为接口是幂等的
            throw new Mall4cloudException(ResponseEnum.EXCEPTION);
        }
    }

    @Override
    public Integer getPayStatusByOrderIds(String orderIds) {
        return payInfoMapper.getPayStatusByOrderIds(orderIds);
    }

    @Override
    public Integer isPay(String orderIds, Long userId) {
        return payInfoMapper.isPay(orderIds, userId);
    }

    @Override
    public PayInfo getByByBizPayNo(String outTradeNo) {
        return payInfoMapper.getByByBizPayNo(outTradeNo);
    }
}
