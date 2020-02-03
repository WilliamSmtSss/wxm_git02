package com.splan.xbet.grpclib.rpc;

import com.splan.xbet.grpclib.lib.Member;
import com.splan.xbet.grpclib.lib.MemberListRequest;
import com.splan.xbet.grpclib.lib.MemberListResponse;
import com.splan.xbet.grpclib.lib.MemberListServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class MemberListServiceImpl extends MemberListServiceGrpc.MemberListServiceImplBase {
    @Override
    public void memberList(MemberListRequest request, StreamObserver<MemberListResponse> responseObserver) {
        Member freewolf = Member.newBuilder()
                .setPassword("123456")
                .setUsername("freewolf")
                .setInfo("hello world")
                .build();

        Member hello = Member.newBuilder()
                .setPassword("123456")
                .setUsername("hello")
                .setInfo("hello wor;d")
                .build();

        MemberListResponse.Builder builder = MemberListResponse.newBuilder();
        builder.addMember(freewolf);
        builder.addMember(hello);
        MemberListResponse response = builder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
