package com.wangmeng.mall.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author wangmeng
 */
//@EnableFeignClients(basePackages = "com.mtcarpenter.mall.client")
@MapperScan("com.wangmeng.mall.api.dao")
@SpringBootApplication(scanBasePackages = "com.wangmeng.mall")
@EnableDiscoveryClient
//@EnableFeignClients(basePackages = "com.wangmeng.mall.client")
public class MallApiProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApiProductApplication.class, args);
    }

}