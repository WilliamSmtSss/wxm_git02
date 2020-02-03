package com.splan.backgateway.backgateway.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class GateWayController {

    @RequestMapping("/gateway/TestTimeout")
    public String timeout(){
        throw new RuntimeException();
    }

}
