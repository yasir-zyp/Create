package com.mall4j.cloud.user.controller.multishop;

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.user.dto.ConsultInfoDTO;
import com.mall4j.cloud.user.dto.InvoiceInfoDTO;
import com.mall4j.cloud.user.service.UserBasisService;
import com.mall4j.cloud.user.vo.ConsultInfoVO;
import com.mall4j.cloud.user.vo.InvoiceInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController("multishopBasisUserController")
@RequestMapping("/m/basis_user")
@Api(tags = "用户基础信息表")
public class UserBasisController {
    @Autowired
    private UserBasisService userBasisService;

    @PostMapping("/add_invoice_info")
    @ApiOperation(value = "添加发票信息", notes = "添加发票信息")
    public ServerResponseEntity<Object> AddInvoiceInfo(@RequestBody InvoiceInfoDTO invoiceInfoDTO) {
        userBasisService.creatInvoiceInfo(invoiceInfoDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","添加成功");
        return ServerResponseEntity.success(map);
    }
    @GetMapping("/find_invoice_info")
    @ApiOperation(value = "查询发票信息", notes = "查询发票信息")
    public ServerResponseEntity<Object> FindInvoiceInfo(@Valid PageDTO pageDTO) {
        PageVO<InvoiceInfoVO> invoiceInfoVOList=userBasisService.findInvoiceInfo(pageDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","查询成功");
        map.put("pages",invoiceInfoVOList.getPages());
        map.put("total",invoiceInfoVOList.getTotal());
        map.put("invoiceInfoVOList",invoiceInfoVOList.getList());
        return ServerResponseEntity.success(map);
    }
    @DeleteMapping("/del_invoice_info")
    @ApiOperation(value = "删除发票信息", notes = "删除发票信息")
    public ServerResponseEntity<Object> DelInvoiceInfo(@RequestParam("invoiceId") Long invoiceId) {
        userBasisService.delInvoiceInfo(invoiceId);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","删除成功");
        return ServerResponseEntity.success(map);
    }
    @PutMapping("/up_invoice_info")
    @ApiOperation(value = "修改发票信息", notes = "删除发票信息")
    public ServerResponseEntity<Object> UpdateInvoiceInfo(@RequestBody InvoiceInfoDTO invoiceInfoDTO) {
        userBasisService.upInvoiceInfo(invoiceInfoDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","修改成功");
        return ServerResponseEntity.success(map);
    }
    @PostMapping("/ec_invoice_info")
    @ApiOperation(value = "回显发票信息", notes = "回显发票信息")
    public ServerResponseEntity<Object> EchoInvoiceInfo(@RequestParam("invoiceId") Long invoiceId) {
        InvoiceInfoVO invoiceInfoVO=userBasisService.echoInvoiceInfo(invoiceId);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","回显成功");
        map.put("invoiceInfoVO",invoiceInfoVO);
        return ServerResponseEntity.success(map);
    }
    @PostMapping("/add_consult_info")
    @ApiOperation(value = "添加需求", notes = "添加需求")
    public ServerResponseEntity<Object> AddConsultInfo(@RequestBody ConsultInfoDTO consultInfoDTO) {
        userBasisService.creatConsultInfo(consultInfoDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","添加成功");
        return ServerResponseEntity.success(map);
    }
    @GetMapping("/find_consult_info")
    @ApiOperation(value = "查询需求", notes = "查询需求")
    public ServerResponseEntity<Object> FindConsultInfo(@Valid PageDTO pageDTO) {
        PageVO<ConsultInfoVO> consultInfoVO=userBasisService.findConsultInfo(pageDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","查询成功");
        map.put("pages",consultInfoVO.getPages());
        map.put("total",consultInfoVO.getTotal());
        map.put("invoiceInfoVOList",consultInfoVO.getList());
        return ServerResponseEntity.success(map);
    }
    @DeleteMapping("/del_consult_info")
    @ApiOperation(value = "删除需求", notes = "删除需求")
    public ServerResponseEntity<Object> DelConsultInfo(@RequestParam("consultId") Long consultId) {
        userBasisService.delConsultInfo(consultId);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","删除成功");
        return ServerResponseEntity.success(map);
    }

    @PostMapping("/ec_consult_info")
    @ApiOperation(value = "回显需求", notes = "回显需求")
    public ServerResponseEntity<Object> EchoConsultInfo(@RequestParam("consultId") Long consultId) {
        ConsultInfoVO consultInfoVO=userBasisService.echoConsultInfo(consultId);
        Map<String,Object> map=new HashMap<>();
        map.put("msg","回显成功");
        map.put("consultInfoVO",consultInfoVO);
        return ServerResponseEntity.success(map);
    }
}
