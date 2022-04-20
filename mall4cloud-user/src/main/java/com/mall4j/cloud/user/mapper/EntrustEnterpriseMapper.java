package com.mall4j.cloud.user.mapper;

import com.mall4j.cloud.user.model.EntrustEnterprise;
import com.mall4j.cloud.user.vo.EntrustEnterpriseVO;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface EntrustEnterpriseMapper {
    int deleteByPrimaryKey(Long entrustEnterpriseId);

    int insert(EntrustEnterprise record);

    int insertSelective(EntrustEnterprise record);

    EntrustEnterprise selectByPrimaryKey(Long entrustEnterpriseId);

    int updateByPrimaryKeySelective(EntrustEnterprise record);

    int updateByPrimaryKey(EntrustEnterprise record);

    EntrustEnterpriseVO echoEntrust(@Param("userId") Long userId);
}