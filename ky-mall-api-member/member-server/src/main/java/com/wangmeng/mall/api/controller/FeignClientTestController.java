package com.wangmeng.mall.api.controller;

import com.wangmeng.mall.client.FeignTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignClientTestController {

    @Autowired
    private FeignTest feign;

    @GetMapping(value = "/echo/{name}")
    public String echo(@PathVariable String name) {
        return feign.echo(name);
    }
}
