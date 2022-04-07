package com.mall4j.cloud.user.mapper;

import com.mall4j.cloud.user.model.ConsultInfo;
import com.mall4j.cloud.user.vo.ConsultInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultInfoMapper {
    int deleteByPrimaryKey(Long consultId);

    int insert(ConsultInfo record);

    int insertSelective(ConsultInfo record);

    ConsultInfo selectByPrimaryKey(Integer consultId);

    int updateByPrimaryKeySelective(ConsultInfo record);

    int updateByPrimaryKey(ConsultInfo record);

    void findConsultInfo(@Param("userId") Long userId);

    ConsultInfoVO selectByPrimaryKeys(@Param("consultId")Long consultId);
}