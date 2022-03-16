package com.mall4j.cloud.user.controller;

import com.mall4j.cloud.api.user.vo.AreaVO;
import com.mall4j.cloud.user.UserTestApplication;
import com.mall4j.cloud.user.service.AreaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserTestApplication.class)
public class test {
    @Autowired
    private AreaService areaService;
    @Test
    public  void contextLoads() {
      areaService.updateArea();
    }
}
