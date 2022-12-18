package com.learn.xiol.docker_boot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author luowei
 * @date 2022-12-16 0:11
 * @desc 订单控制层
 */
@RestController
public class OrderController {

    @Value("${server.port}")
    private String port;

    @Value("${spring.application.name}")
    private String applicationName;


    @RequestMapping("/order/docker")
    public String helloDocker(){
        return "hello docker" + "\t" + applicationName + "\t" + port
                + "\t"  + UUID.randomUUID().toString();
    }

    @RequestMapping(value = "/order/index", method = RequestMethod.GET)
    public String index(){
        return "服务名：" + "\t" + applicationName + ",服务端口号：" + "\t" + port
                + "\t"  + UUID.randomUUID().toString();
    }
}
