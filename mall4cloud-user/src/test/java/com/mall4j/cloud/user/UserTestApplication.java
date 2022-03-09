package com.mall4j.cloud.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.mall4j.cloud" })
public class UserTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
