package com.mall4j.cloud.multishop.mapper;

import com.mall4j.cloud.multishop.model.ShopQualification;
import com.mall4j.cloud.multishop.vo.ShopQualificationVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopQualificationMapper {
    /*
    * 删除资质信息
    * */
    int deleteByPrimaryKey(Long shopQualificationId);

    int insert(ShopQualification record);
    /*
    * 添加资质信息
    * */
    int insertSelective(ShopQualification record);

    ShopQualification selectByPrimaryKey(Long shopQualificationId);

    int updateByPrimaryKeySelective(ShopQualification record);

    int updateByPrimaryKey(ShopQualification record);

    ShopQualificationVO findAptitudeByToken(@Param("tenantId") Long tenantId);
}