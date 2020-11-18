package com.wangmeng.mall.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author mtcarpenter
 * @github https://github.com/mtcarpenter/mall-cloud-alibaba
 * @desc 微信公众号：山间木匠
 */
@SpringBootApplication(scanBasePackages = "com.wangmeng.mall")
@MapperScan("com.wangmeng.mall.api.dao")
@EnableFeignClients(basePackages = "com.wangmeng.mall.client")
public class MallApiMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallApiMemberApplication.class, args);
    }
}
