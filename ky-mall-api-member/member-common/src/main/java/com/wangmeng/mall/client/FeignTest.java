package com.wangmeng.mall.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ky-mall-api-order")
public interface FeignTest {
    /**
     * 请求服务提供方的 接口
     * @param name
     * @return
     */
    @GetMapping("/echo/{name}")
    String echo(@PathVariable(value = "name") String name);
}
