package com.wangmeng.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route")
public class GatewayTestController {

    @GetMapping(value = "")
    public String hello() {
        return "8090:hello";
    }

    @GetMapping(value = "/sayHello/{name}")
    public String sayHello(@PathVariable String name) {
        return "8090:sayHello:" + name;
    }
}
