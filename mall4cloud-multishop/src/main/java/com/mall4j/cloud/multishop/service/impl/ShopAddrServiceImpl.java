package com.mall4j.cloud.multishop.service.impl;

import com.github.pagehelper.ISelect;
import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.util.PageUtil;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.multishop.mapper.ShopAddrMapper;
import com.mall4j.cloud.multishop.service.ShopAddrService;
import com.mall4j.cloud.multishop.vo.ShopAddrVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopAddrServiceImpl implements ShopAddrService {
    @Autowired
    private ShopAddrMapper shopAddrMapper;


    @Override
    public List<ShopAddrVO> findBusinessAddress(Long tenantId) {
        List<ShopAddrVO> shopAddrVOList=shopAddrMapper.findBusinessAddress(tenantId);
        for (int i = 0; i <shopAddrVOList.size() ; i++) {
            Integer provinceId= Math.toIntExact(shopAddrVOList.get(i).getProvinceId());
            Integer cityId= Math.toIntExact(shopAddrVOList.get(i).getCityId());
            Integer areaId= Math.toIntExact(shopAddrVOList.get(i).getAreaIds());
            int array[] ={provinceId,cityId,areaId};
            shopAddrVOList.get(i).setAreaId(array);
        }
        return shopAddrVOList;
    }
}
