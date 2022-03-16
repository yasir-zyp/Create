package com.mall4j.cloud.product.controller.admin;

import cn.hutool.core.collection.CollUtil;
import com.mall4j.cloud.api.product.vo.AttrVO;
import com.mall4j.cloud.api.product.vo.CategoryVO;
import com.mall4j.cloud.common.constant.Constant;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;

import com.mall4j.cloud.product.dto.AttrDTO;
import com.mall4j.cloud.product.model.Attr;
import com.mall4j.cloud.product.service.AttrService;
import com.mall4j.cloud.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 属性信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:23
 */
@RestController("platformAttrController")
@RequestMapping("/admin/attr")
@Api(tags = "admin-检测项目信息")
public class AttrController {

    @Autowired
    private AttrService attrService;

    @Autowired
	private MapperFacade mapperFacade;
    @Autowired
    private CategoryService categoryService;

	@GetMapping("/page")
	@ApiOperation(value = "获取检测项目列表", notes = "分页获取检测项列表")
	public ServerResponseEntity<PageVO<AttrVO>> page(@Valid PageDTO pageDTO, AttrDTO attrDTO) {
		PageVO<AttrVO> attrPage = attrService.page(pageDTO, attrDTO);
		return ServerResponseEntity.success(attrPage);
	}

	@GetMapping
    @ApiOperation(value = "获取检测项目信息", notes = "根据attrId获取检测项目信息")
    public ServerResponseEntity<AttrVO> getByAttrId(@RequestParam Long attrId) {
        return ServerResponseEntity.success(attrService.getByAttrId(attrId));
    }

    @PostMapping
    @ApiOperation(value = "保存检测项目信息", notes = "保存检测项目信息")
    public ServerResponseEntity<Void> save(@Valid @RequestBody AttrDTO attrDTO) {
        if (Objects.isNull(attrDTO.getAttrType())) {
            throw new Mall4cloudException("检测项目资质不能为空");
        }
        if (Objects.isNull(attrDTO.getCategoryName())) {
            throw new Mall4cloudException("检测项目类别不能为空");
        }
        CategoryVO categoryVO=categoryService.getByName(attrDTO.getCategoryName());
        attrDTO.setCategoryId(categoryVO.getCategoryId());
	    checkAttrInfo(attrDTO);
        Attr attr = mapperFacade.map(attrDTO, Attr.class);
        attrService.save(attr);
        return ServerResponseEntity.success();
    }

    @PutMapping
    @ApiOperation(value = "更新检测项目信息", notes = "更新检测项目信息")
    public ServerResponseEntity<Void> update(@Valid @RequestBody AttrDTO attrDTO) {
        checkAttrInfo(attrDTO);
        Attr attr = mapperFacade.map(attrDTO, Attr.class);
        attrService.update(attr);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @ApiOperation(value = "删除检测项目信息", notes = "根据属性信息id删除检测项目信息")
    public ServerResponseEntity<Void> delete(@RequestParam Long attrId) {
        attrService.deleteById(attrId);
        return ServerResponseEntity.success();
    }

    /**
     * 校验属性数据
     * @param attrDTO
     */
    private void checkAttrInfo(AttrDTO attrDTO) {

        if (Objects.isNull(attrDTO.getCategoryId())) {
            throw new Mall4cloudException("分类名称不正确");
        }
        if (Objects.isNull(attrDTO.getAttrType())) {
            throw new Mall4cloudException("资质类别不能为空");
        }
        if (Objects.isNull(attrDTO.getSearchType())) {
            throw new Mall4cloudException("搜索属性不能为空");
        }
    }


}
