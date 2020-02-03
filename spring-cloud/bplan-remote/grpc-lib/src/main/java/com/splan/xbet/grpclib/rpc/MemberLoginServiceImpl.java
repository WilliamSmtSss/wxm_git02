package com.splan.xbet.grpclib.rpc;

import com.splan.xbet.grpclib.lib.*;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.lognet.springboot.grpc.GRpcService;

@GrpcService(MemberOuterClass.class)
public class MemberLoginServiceImpl extends MemberLoginServiceGrpc.MemberLoginServiceImplBase {

    @Override
    public void memberLogin(MemberLoginRequest request, StreamObserver<MemberLoginResponse> responseObserver) {

        String token = "";
        if(request.getMember().getPassword().equals("123456")){
            token = "success";
        }

        MemberLoginResponse response = MemberLoginResponse
                .newBuilder()
                .setToken(token)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
