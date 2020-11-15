package com.wangmeng.mall.api.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.mtcarpenter.mall.mapper","com.wangmeng.mall.api.dao"})
public class MyBatisConfig {
}
