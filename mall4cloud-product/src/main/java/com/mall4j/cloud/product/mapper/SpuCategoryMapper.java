package com.mall4j.cloud.product.mapper;

import com.mall4j.cloud.api.product.vo.SpuCategoryVO;
import com.mall4j.cloud.product.model.SpuCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpuCategoryMapper {
    int deleteByPrimaryKey(Long spuCategoryId);

    int insert(SpuCategory record);

    int insertSelective(SpuCategory record);

    SpuCategory selectByPrimaryKey(Long spuCategoryId);

    int updateByPrimaryKeySelective(SpuCategory record);

    int updateByPrimaryKey(SpuCategory record);

    List<String> SpuCategoryById(@Param("spuId") Long spuId);
}