package com.mall4j.cloud.product.service.impl;

import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.api.product.vo.CategoryVO;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.util.PageAdapter;
import com.mall4j.cloud.common.database.util.PageUtil;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.product.dto.AttrDTO;
import com.mall4j.cloud.product.dto.SpuIdDTO;
import com.mall4j.cloud.product.model.Attr;
import com.mall4j.cloud.product.mapper.AttrMapper;
import com.mall4j.cloud.product.model.Category;
import com.mall4j.cloud.product.service.*;
import com.mall4j.cloud.api.product.vo.AttrVO;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
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
    @Autowired
    private CategoryService categoryService;
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
        Integer array[]={Math.toIntExact(attrVO.getCategoryIdOne()), Math.toIntExact(attrVO.getCategoryIdTwo()), Math.toIntExact(attrVO.getCategoryId())};
        attrVO.setCategoryNames(array);
        if (Objects.isNull(attrVO)) {
            throw new Mall4cloudException("检测项目不存在或已删除");
        }
        return attrVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Attr attr) {
        UserInfoInTokenBO userInfoInToken = AuthUserContext.get();
        attr.setShopId(userInfoInToken.getTenantId());
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
       if (attr.getCategoryNames()!=null){
           Integer array[]=attr.getCategoryNames();
           Long id=(long)array[2];
           CategoryVO categoryVO=categoryService.getNameById(id);
           attr.setCategoryId((long)array[2]);
           attr.setCategoryName(categoryVO.getName());
           attr.setCategoryIdOne((long)array[0]);
           attr.setCategoryIdTwo((long)array[1]);
       }
        attrMapper.updateByPrimaryKeySelective(attr);
    }

    @Override
    public void deleteById(Long attrId) {
        AttrVO dbAttr = getByAttrId(attrId);
        if (Objects.isNull(dbAttr)) {
            throw new Mall4cloudException("该检测项已删除或不存在");
        }

        attrMapper.deleteById(attrId);
    }

    @Override
    public String batchImport(String fileName, MultipartFile file) {
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new Mall4cloudException("上传文件格式不正确");
        }
        List<Attr> khs = new ArrayList<>();
        try{
            InputStream is = file.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(is));
            //有多少个sheet
            int sheets = workbook.getNumberOfSheets();
            UserInfoInTokenBO userInfoInToken = AuthUserContext.get();
            for (int i = 0; i < sheets; i++) {
                HSSFSheet sheet = workbook.getSheetAt(i);
                //获取多少行
                int rows = sheet.getPhysicalNumberOfRows();
                Attr attr = null;
                for (int j = 1; j < rows; j++) {
                    attr = new Attr();
                    //获得第 j 行
                    HSSFRow row = sheet.getRow(j);
                    /*属性名称*/
                    attr.setName(row.getCell(0).getStringCellValue());
                    /*检测项目英文名称*/
                    attr.setNameEnglish(row.getCell(1).getStringCellValue());
                    /*标准号*/
                    attr.setStandard(row.getCell(2).getStringCellValue());
                    /*标准名称*/
                    attr.setStandardName(row.getCell(3).getStringCellValue());;
                    /*技术要求/限量*/
                    attr.setRequirement(row.getCell(4).getStringCellValue());
                    /*检测周期(工作日)*/
                    attr.setCycle((int) row.getCell(5).getNumericCellValue());
                    /*检测价格*/
                    attr.setPrice((long) row.getCell(6).getNumericCellValue());
                    /*检测资质0:cma，1:cnas，2：cma，cnas*/
                    attr.setAttrType((int) row.getCell(7).getNumericCellValue());
                    /*分类名称*/
                    attr.setCategoryName(row.getCell(8).getStringCellValue());
                    //用分类名称查上级
                    CategoryVO categoryVO=categoryService.findIdByName(attr.getCategoryName());
                    /*第三级id*/
                    attr.setCategoryId(categoryVO.getCategoryId());
                    //用parentId查询二级名称
                    CategoryVO categoryVO1=categoryService.getById(categoryVO.getParentId());
                    /*第二级id*/
                    attr.setCategoryIdTwo(categoryVO1.getCategoryId());
                    /*查询一级id*/
                    CategoryVO categoryVO2=categoryService.getById(categoryVO1.getParentId());
                    attr.setCategoryIdOne(categoryVO2.getCategoryId());
                    /*价格备注*/
                    attr.setDesc(row.getCell(9).getStringCellValue());
                    /*店铺Id*/
                    attr.setShopId(userInfoInToken.getTenantId());
                    khs.add(attr);
                    attrMapper.insertSelective(attr);
                }
            }
        }catch(Exception e){
            throw new Mall4cloudException("导入数据格式有误，请检查上传文件");
        }
        return "导入数据成功";
    }


}
