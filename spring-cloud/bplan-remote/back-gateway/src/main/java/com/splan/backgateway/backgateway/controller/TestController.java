//package com.splan.backgateway.backgateway.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class TestController {
//
//    @Autowired
//    private OAuth2RestTemplate oAuth2RestTemplate;
//
//    @PostMapping("/getToken")
//    public String getToken(){
//        return oAuth2RestTemplate.getAccessToken().getValue();
//    }
//
//}
