package com.mall4j.cloud.product.service;

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.product.dto.AttrDTO;
import com.mall4j.cloud.product.dto.SpuIdDTO;
import com.mall4j.cloud.product.model.Attr;
import com.mall4j.cloud.api.product.vo.AttrVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 属性信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:23
 */
public interface AttrService {

	/**
	 * 分页获取属性信息列表
	 * @param pageDTO 分页参数
	 * @param attrDTO
	 * @return 属性信息列表分页数据
	 */
	PageVO<AttrVO> page(PageDTO pageDTO, AttrDTO attrDTO);

	/**
	 * 根据属性信息id获取属性信息
	 *
	 * @param attrId 属性信息id
	 * @return 属性信息
	 */
	AttrVO getByAttrId(Long attrId);

	/**
	 * 保存属性信息
	 * @param attr 属性信息
	 */
	void save(Attr attr);

	/**
	 * 更新属性信息
	 * @param attr 属性信息
	 */
	void update(Attr attr);

	/**
	 * 根据属性信息id删除属性信息
	 * @param attrId
	 */
	void deleteById(Long attrId);

	String batchImport(String fileName, MultipartFile file);
}
