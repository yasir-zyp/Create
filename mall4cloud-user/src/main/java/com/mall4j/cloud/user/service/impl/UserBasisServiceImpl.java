package com.mall4j.cloud.user.service.impl;

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.util.PageUtil;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.user.dto.ConsultInfoDTO;
import com.mall4j.cloud.user.dto.InvoiceInfoDTO;
import com.mall4j.cloud.user.mapper.ConsultInfoMapper;
import com.mall4j.cloud.user.mapper.InvoiceInfoMapper;
import com.mall4j.cloud.user.model.ConsultInfo;
import com.mall4j.cloud.user.model.InvoiceInfo;
import com.mall4j.cloud.user.service.UserBasisService;
import com.mall4j.cloud.user.vo.ConsultInfoVO;
import com.mall4j.cloud.user.vo.InvoiceInfoVO;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBasisServiceImpl implements UserBasisService {
    @Autowired
    private InvoiceInfoMapper invoiceInfoMapper;
    @Autowired
    private MapperFacade mapperFacade;
    @Autowired
    private ConsultInfoMapper consultInfoMapper;

    @Override
    public void creatInvoiceInfo(InvoiceInfoDTO invoiceInfoDTO) {
        InvoiceInfo invoiceInfo=mapperFacade.map(invoiceInfoDTO, InvoiceInfo.class);
        invoiceInfoMapper.insertSelective(invoiceInfo);
    }

    @Override
    public PageVO<InvoiceInfoVO> findInvoiceInfo(PageDTO pageDTO) {
        Long userId = AuthUserContext.get().getUserId();
        return PageUtil.doPage(pageDTO, ()->invoiceInfoMapper.findInvoiceInfo(userId));
    }

    @Override
    public void delInvoiceInfo(Long invoiceId) {
        invoiceInfoMapper.deleteByPrimaryKey(invoiceId);
    }

    @Override
    public void upInvoiceInfo(InvoiceInfoDTO invoiceInfoDTO) {
        InvoiceInfo invoiceInfo=mapperFacade.map(invoiceInfoDTO, InvoiceInfo.class);
        invoiceInfoMapper.updateByPrimaryKeySelective(invoiceInfo);
    }

    @Override
    public InvoiceInfoVO echoInvoiceInfo(Long invoiceId) {
        return invoiceInfoMapper.selectByPrimaryKeys(invoiceId);
    }

    @Override
    public void creatConsultInfo(ConsultInfoDTO consultInfoDTO) {
        Long userId = AuthUserContext.get().getUserId();
        ConsultInfo consultInfo=mapperFacade.map(consultInfoDTO, ConsultInfo.class);
        consultInfo.setUserId(userId);
        consultInfoMapper.insertSelective(consultInfo);
    }

    @Override
    public PageVO<ConsultInfoVO> findConsultInfo(PageDTO pageDTO) {
        Long userId = AuthUserContext.get().getUserId();
        return PageUtil.doPage(pageDTO, ()->consultInfoMapper.findConsultInfo(userId));
    }

    @Override
    public void delConsultInfo(Long consultId) {
        consultInfoMapper.deleteByPrimaryKey(consultId);
    }

    @Override
    public ConsultInfoVO echoConsultInfo(Long consultId) {
        return consultInfoMapper.selectByPrimaryKeys(consultId);
    }
}
