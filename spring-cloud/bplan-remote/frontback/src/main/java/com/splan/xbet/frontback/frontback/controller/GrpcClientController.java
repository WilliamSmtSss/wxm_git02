//package com.splan.xbet.frontback.frontback.controller;
//
//import com.splan.xbet.frontback.frontback.grpc.GrpcClientService;
//import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class GrpcClientController {
//
//    @Autowired
//    private GrpcClientService grpcClientService;
//
//    @RequestMapping("/grpc/test")
//    public String printMessage(@RequestParam(defaultValue = "Michael") String name) {
//        return grpcClientService.sendMessage(name);
//    }
//
//}
//
