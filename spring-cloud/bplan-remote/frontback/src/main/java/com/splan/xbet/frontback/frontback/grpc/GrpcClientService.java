//package com.splan.xbet.frontback.frontback.grpc;
//
//import io.grpc.Channel;
//import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
//import org.springframework.stereotype.Service;
//
//@Service
//public class GrpcClientService {
//
//    @GrpcClient("local-grpc-server")
//    private Channel serverChannel;
//
//    public String sendMessage(String name) {
//        GreeterGrpc.GreeterBlockingStub stub= GreeterGrpc.newBlockingStub(serverChannel);
//        GreeterOuterClass.HelloReply response = stub.sayHello(GreeterOuterClass.HelloRequest.newBuilder().setName(name).build());
//        return response.getMessage();
//    }
//
//}
//
