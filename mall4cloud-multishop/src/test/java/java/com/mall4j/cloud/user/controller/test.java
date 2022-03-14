package java.com.mall4j.cloud.user.controller;


import com.mall4j.cloud.api.auth.bo.UserInfoInTokenBO;
import com.mall4j.cloud.multishop.controller.platform.ShopDetailController;
import com.mall4j.cloud.multishop.dto.BasicInformationDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.com.mall4j.cloud.user.MultishopTestApplication;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MultishopTestApplication.class)
public class test {
@Autowired
private ShopDetailController shopDetailController;
    @Test
    public  void contextLoads() {
        BasicInformationDTO basicInformationDTO=new BasicInformationDTO();
        basicInformationDTO.setShopName("叶少的商店");
        shopDetailController.basicInformation(basicInformationDTO);
    }
}
