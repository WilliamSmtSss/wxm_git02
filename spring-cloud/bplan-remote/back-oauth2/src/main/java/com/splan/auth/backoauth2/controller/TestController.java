package com.splan.auth.backoauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class TestController {
    @GetMapping(value = "/order/1")
    public String getString(){
        return "haha";
    }
}
