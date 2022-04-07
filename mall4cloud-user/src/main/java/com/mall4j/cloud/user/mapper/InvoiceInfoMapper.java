package com.mall4j.cloud.user.mapper;

import com.mall4j.cloud.user.model.InvoiceInfo;
import com.mall4j.cloud.user.vo.InvoiceInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceInfoMapper {
    int deleteByPrimaryKey(Long invoiceId);

    int insert(InvoiceInfo record);

    int insertSelective(InvoiceInfo record);

    InvoiceInfo selectByPrimaryKey(Long invoiceId);

    int updateByPrimaryKeySelective(InvoiceInfo record);

    int updateByPrimaryKey(InvoiceInfo record);

    List<InvoiceInfoVO> findInvoiceInfo(@Param("userId") Long userId);

    InvoiceInfoVO selectByPrimaryKeys(@Param("invoiceId")Long invoiceId);
}