package com.mall4j.cloud.product.controller;

import com.mall4j.cloud.product.ProductTestApplication;
import com.mall4j.cloud.product.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductTestApplication.class)
public class test {
@Autowired
    private CategoryService categoryService;
    @Test
    public  void contextLoads() {
categoryService.updateAll();
    }
}
