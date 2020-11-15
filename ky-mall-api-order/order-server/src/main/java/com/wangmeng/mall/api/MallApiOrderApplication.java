package com.wangmeng.mall.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wangmeng
 */
//@EnableFeignClients(basePackages = "com.mtcarpenter.mall.client")
@MapperScan("com.wangmeng.mall.api.dao")
@SpringBootApplication(scanBasePackages = "com.wangmeng.mall")
public class MallApiOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApiOrderApplication.class, args);
    }

}