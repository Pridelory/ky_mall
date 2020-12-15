package com.wangmeng.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.wangmeng.mall.api.dao")
@SpringBootApplication
public class NewBeeMallManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewBeeMallManageApplication.class, args);
    }

}
