package com.mall4j.cloud.product.controller.admin;


import com.mall4j.cloud.api.product.vo.AttrVO;
import com.mall4j.cloud.api.product.vo.CategoryVO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.response.ServerResponseEntity;

import com.mall4j.cloud.product.dto.AttrDTO;
import com.mall4j.cloud.product.dto.SpuIdDTO;
import com.mall4j.cloud.product.model.Attr;
import com.mall4j.cloud.product.service.AttrService;
import com.mall4j.cloud.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public ServerResponseEntity<Object> page(@Valid PageDTO pageDTO, AttrDTO attrDTO) {
		PageVO<AttrVO> attrPage = attrService.page(pageDTO, attrDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","查询成功");
        map.put("data",attrPage.getList());
        map.put("total",attrPage.getTotal());
        map.put("pages",attrPage.getPages());
		return ServerResponseEntity.success(map);
	}

	@GetMapping("/echo_attr")
    @ApiOperation(value = "回显检测项目信息", notes = "根据attrId获取检测项目信息")
    public ServerResponseEntity<Object> getByAttrId(@RequestParam Long attrId) {
        Map<String,Object> map=new HashMap<>();
        map.put("msg","查询成功");
        map.put("attrVO", attrService.getByAttrId(attrId));
        return ServerResponseEntity.success(map);
    }

    @PostMapping("/add_attr")
    @ApiOperation(value = "添加检测项目信息", notes = "保存检测项目信息")
    public ServerResponseEntity<Object> save(@Valid @RequestBody AttrDTO attrDTO) {
        if (Objects.isNull(attrDTO.getAttrType())) {
            throw new Mall4cloudException("检测项目资质不能为空");
        }
        if (Objects.isNull(attrDTO.getCategoryNames())) {
            throw new Mall4cloudException("检测项目类别不能为空");
        }
        Integer array[]=attrDTO.getCategoryNames();
        Long id=(long)array[2];
        CategoryVO categoryVO=categoryService.getNameById(id);
        attrDTO.setCategoryId((long)array[2]);
        attrDTO.setCategoryName(categoryVO.getName());
        attrDTO.setCategoryIdOne((long)array[0]);
        attrDTO.setCategoryIdTwo((long)array[1]);
	    checkAttrInfo(attrDTO);
        Attr attr = mapperFacade.map(attrDTO, Attr.class);
        attrService.save(attr);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","添加完成");
        return ServerResponseEntity.success(map);
    }

    @PutMapping("/up_attr")
    @ApiOperation(value = "更新检测项目信息", notes = "更新检测项目信息")
    public ServerResponseEntity<Object> update(@Valid @RequestBody AttrDTO attrDTO) {
        checkAttrInfo(attrDTO);
        Attr attr = mapperFacade.map(attrDTO, Attr.class);
        attrService.update(attr);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","修改成功");
        return ServerResponseEntity.success(map);
    }

    @DeleteMapping("/del_attr")
    @ApiOperation(value = "删除检测项目信息", notes = "根据属性信息id删除检测项目信息")
    public ServerResponseEntity<Object> delete(@RequestParam Long attrId) {
        attrService.deleteById(attrId);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","删除成功");
        return ServerResponseEntity.success(map);
    }
    /**
     * excel导入
     */
    @PostMapping("/upload")
    @ApiOperation(value = "excel导入检测项", notes = "excel导入检测项")
    public ServerResponseEntity<Object> addKHexcel(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String test =  attrService.batchImport(fileName, file);
        Map<String,Object> map=new HashMap<>();
        map.put("msg",test);
        return ServerResponseEntity.success(map);
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
