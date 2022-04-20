package com.mall4j.cloud.user.service;

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.user.dto.ConsultInfoDTO;
import com.mall4j.cloud.user.dto.InvoiceInfoDTO;
import com.mall4j.cloud.user.vo.ConsultInfoVO;
import com.mall4j.cloud.user.vo.InvoiceInfoVO;

public interface UserBasisService {
    void creatInvoiceInfo(InvoiceInfoDTO invoiceInfoDTO);

    PageVO<InvoiceInfoVO> findInvoiceInfo(PageDTO pageDTO);

    void delInvoiceInfo(Long invoiceId);

    void upInvoiceInfo(InvoiceInfoDTO invoiceInfoDTO);

    InvoiceInfoVO echoInvoiceInfo(Long invoiceId);

    void creatConsultInfo(ConsultInfoDTO consultInfoDTO);

    PageVO<ConsultInfoVO> findConsultInfo(PageDTO pageDTO);

    void delConsultInfo(Long consultId);

    ConsultInfoVO echoConsultInfo(Long consultId);
}
