package com.mall4j.cloud.api.payment.feign;

import com.mall4j.cloud.api.payment.bo.PayInfoBo;
import com.mall4j.cloud.api.payment.dto.PayInfoDto;
import com.mall4j.cloud.common.feign.FeignInsideAuthConfig;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author zyp
 * @date 2022/5/13
 */
@FeignClient(value = "mall4cloud-payment",contextId = "pay")
public interface PaymentFeignClient {

    /**
     * 内部支付接口
     * @param request request
     * @return 配置信息json
     */
    @PostMapping(value = FeignInsideAuthConfig.FEIGN_INSIDE_URL_PREFIX + "/insider/pay")
    ServerResponseEntity<PayInfoBo> pay(HttpServletRequest request, @Valid @RequestParam("payInfoDto") PayInfoDto payParam);

}
