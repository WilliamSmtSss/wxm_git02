package com.splan.resource.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping(value = "/resource/hello")
    public String test1(){
        return  "success!";
    }

}
