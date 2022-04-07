package com.mall4j.cloud.multishop.service;

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.multishop.vo.ShopAddrVO;

import java.util.List;

public interface ShopAddrService {


    List<ShopAddrVO> findBusinessAddress(Long tenantId);
}
