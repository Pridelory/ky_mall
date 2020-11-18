package com.wangmeng.mall.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangmeng
 */
//@EnableFeignClients(basePackages = "com.mtcarpenter.mall.client")
@MapperScan("com.wangmeng.mall.api.dao")
@SpringBootApplication(scanBasePackages = "com.wangmeng.mall")
@EnableDiscoveryClient
public class MallApiOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallApiOrderApplication.class, args);
    }
}