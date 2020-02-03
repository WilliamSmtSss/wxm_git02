//package com.splan.xbet.admin.back.service.impl;
//import com.splan.xbet.admin.back.service.Oauth2Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestOperations;
//
//import java.net.URI;
//
//@Service
//public class Oauth2ServiceImpl implements Oauth2Service {
//
//    @Autowired
//    private RestOperations helloRestTemplate;
//
//    @Override
//    public String getDataFromResoureServer() {
//
//        String data= helloRestTemplate.getForObject(URI.create("http://localhost:9310/resource/hello"), String.class);//请求资源服务器资源的路径
//        return data;
//
//    }
//
//}
