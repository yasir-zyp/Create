package com.mall4j.cloud.multishop.controller;


import com.mall4j.cloud.multishop.dto.BasicInformationDTO;
import com.mall4j.cloud.multishop.service.ShopDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mall4j.cloud.multishop.MultishopTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MultishopTestApplication.class)
public class testShop {
    @Autowired
     private ShopDetailService shopDetailService;
    @Test
    public  void basicInformation() {

    }
}
