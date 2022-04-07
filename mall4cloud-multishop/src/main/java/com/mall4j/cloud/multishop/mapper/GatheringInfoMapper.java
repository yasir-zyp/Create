package com.mall4j.cloud.multishop.mapper;

import com.mall4j.cloud.multishop.model.GatheringInfo;
import com.mall4j.cloud.multishop.vo.GatheringInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatheringInfoMapper {
    int deleteByPrimaryKey(Long gatheringInfoId);

    int insert(GatheringInfo record);

    int insertSelective(GatheringInfo record);

    GatheringInfo selectByPrimaryKey(Long gatheringInfoId);

    int updateByPrimaryKeySelective(GatheringInfo record);

    int updateByPrimaryKey(GatheringInfo record);

    List<GatheringInfoVO> findAccountInfor(@Param("tenantId") Long tenantId);

    GatheringInfoVO selectByPrimaryKeys(@Param("gatheringInfoId")Long gatheringInfoId);
}