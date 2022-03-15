package com.mall4j.cloud.multishop.mapper;

import com.mall4j.cloud.multishop.model.ShopDetail;
import com.mall4j.cloud.multishop.model.ShopQualification;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopQualificationMapper {
    /**
     * 保存资质详情
     *
     * @param shopQualification 资质详情
     */
    void save(@Param("shopQualification") ShopQualification shopQualification);

    void deleteById(@Param("shopQualificationId") Long shopQualificationId);
}
