package com.mall4j.cloud.payment.controller;

import cn.hutool.core.util.StrUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.result.BaseWxPayResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.mall4j.cloud.payment.bo.PayInfoResultBO;
import com.mall4j.cloud.payment.model.PayInfo;
import com.mall4j.cloud.payment.service.PayInfoService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author FrozenWatermelon
 */
@ApiIgnore
@RestController
@RequestMapping("/ua/notice/pay")
public class PayNoticeController {

    @Autowired
    private PayInfoService payInfoService;
    @Autowired
    private WxPayService wxPayService;

    /**
     * 支付异步回调
     */
    @RequestMapping("/order")
    public String submit(HttpServletRequest request, HttpServletResponse response) {


        String resXml = "";
        try {
            /*
             * HttpSession session = request.getSession(); String mobile = (String)
             * session.getAttribute("userphone"); if (StringUtils.isBlank(mobile)) { return
             * R.error(401, "session获取不到授权手机号！"); } //获取用户手机号,根据用户手机号获取用户ID
             * AuthorizationEntity user = authorizationService.getOneByMobile(mobile);
             */
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlResult);
            // 结果正确 outTradeNo
            String outTradeNo = notifyResult.getOutTradeNo();
            String tradeNo = notifyResult.getTransactionId();
            Integer totalFee = notifyResult.getTotalFee();
            // 自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
            PayInfo payInfo = payInfoService.getByByBizPayNo(outTradeNo);
            int payStatus=payInfo.getPayStatus();
            if(payStatus ==0){
                String[] orderIdStrArr = payInfo.getOrderIds().split(StrUtil.COMMA);
                List<Long> orderIdList = new ArrayList<>();
                for (String s : orderIdStrArr) {
                    orderIdList.add(Long.valueOf(s));
                }
                PayInfoResultBO payInfoResult = new PayInfoResultBO();
                payInfoResult.setPayId(payInfo.getPayId());
                payInfoResult.setBizPayNo(payInfo.getBizPayNo());
                payInfoResult.setCallbackContent(tradeNo);
                // 支付成功
                payInfoService.paySuccess(payInfoResult,orderIdList);
            }

            // 通知微信.异步确认成功.必写.不然会一直通知后台.十次之后就认为交易失败了.
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            response.setContentType("text/xml");
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
//            return WxPayNotifyResponse.success("成功");
            return resXml;
        } catch (Exception e) {
             //WxPayNotifyResponse.fail(e.getMessage());

            return WxPayNotifyResponse.success("code:" + 9999 + "微信回调结果异常,异常原因:" + e.getMessage());
        }



    }
}
