package com.mall4j.cloud.multishop.controller;


import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.multishop.dto.BasicInformationDTO;
import com.mall4j.cloud.multishop.dto.ShopQualificationDTO;
import com.mall4j.cloud.multishop.service.ShopDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mall4j.cloud.multishop.MultishopTestApplication;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MultishopTestApplication.class)
public class testShop {
    @Autowired
     private ShopDetailService shopDetailService;
    @Test
    public  void basicInformation() {
        UserInfoInTokenBO userInfoInTokenBO=new UserInfoInTokenBO();
        List<ShopQualificationDTO> shopQualificationDTOList=new ArrayList<>();
        shopQualificationDTOList.get(0).setShopQualificationId((long) 27);
        shopQualificationDTOList.get(1).setQualificationUrl("54545454");
        shopDetailService.creatShopQualification(shopQualificationDTOList,userInfoInTokenBO);
    }
}
