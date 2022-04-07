package com.mall4j.cloud.multishop.mapper;

import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.multishop.model.ShopAddr;
import com.mall4j.cloud.multishop.vo.ShopAddrVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopAddrMapper {
    int deleteByPrimaryKey(Long shopAddrId);

    int insert(ShopAddr record);

    int insertSelective(ShopAddr record);

    ShopAddr selectByPrimaryKey(Long shopAddrId);

    ShopAddrVO selectByPrimaryKeys(Long shopAddrId);

    int updateByPrimaryKeySelective(ShopAddr record);

    int updateByPrimaryKey(ShopAddr record);

    void updateIsDefault(@Param("shopId") Long shopId);

    List<ShopAddrVO> findBusinessAddress(@Param("tenantId")Long tenantId);
}