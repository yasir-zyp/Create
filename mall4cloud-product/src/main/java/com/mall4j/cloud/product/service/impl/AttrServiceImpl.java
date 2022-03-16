package com.mall4j.cloud.product.service.impl;

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.util.PageAdapter;
import com.mall4j.cloud.common.database.util.PageUtil;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.product.dto.AttrDTO;
import com.mall4j.cloud.product.model.Attr;
import com.mall4j.cloud.product.mapper.AttrMapper;
import com.mall4j.cloud.product.service.*;
import com.mall4j.cloud.api.product.vo.AttrVO;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * 属性信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:23
 */
@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    private AttrMapper attrMapper;
    @Autowired
    private AttrCategoryService attrCategoryService;

    @Override
    public PageVO<AttrVO> page(PageDTO pageDTO, AttrDTO attrDTO) {
        PageVO<AttrVO> pageVO = new PageVO<>();
        attrDTO.setShopId(AuthUserContext.get().getTenantId());
        pageVO.setList(attrMapper.list(new PageAdapter(pageDTO), attrDTO));
        pageVO.setTotal(attrMapper.countAttr(attrDTO));
        pageVO.setPages(PageUtil.getPages(pageVO.getTotal(), pageDTO.getPageSize()));
        return pageVO;
    }

    @Override
    public AttrVO getByAttrId(Long attrId) {
        AttrVO attrVO = attrMapper.getByAttrId(attrId);
        if (Objects.isNull(attrVO)) {
            throw new Mall4cloudException("检测项目不存在或已删除");
        }
        return attrVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Attr attr) {
        attr.setShopId(AuthUserContext.get().getTenantId());
        attrMapper.save(attr);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Attr attr) {
        AttrVO dbAttr = attrMapper.getByAttrId(attr.getAttrId());
        // 属性名、描述相等，则设为null，不进行修改操作
        if (Objects.equals(attr.getName(), dbAttr.getName())) {
            attr.setName(null);
        }
        if (Objects.equals(attr.getDesc(), dbAttr.getDesc())) {
            attr.setDesc(null);
        }

        attrMapper.update(attr);
    }

    @Override
    public void deleteById(Long attrId) {
        AttrVO dbAttr = getByAttrId(attrId);
        if (Objects.isNull(dbAttr)) {
            throw new Mall4cloudException("该检测项已删除或不存在");
        }

        attrMapper.deleteById(attrId);
    }






}
