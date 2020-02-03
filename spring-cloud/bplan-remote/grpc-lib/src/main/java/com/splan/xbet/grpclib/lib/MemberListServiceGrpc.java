package com.splan.xbet.grpclib.lib;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.23.0)",
    comments = "Source: MemberListSerbice.proto")
public final class MemberListServiceGrpc {

  private MemberListServiceGrpc() {}

  public static final String SERVICE_NAME = "com.splan.xbet.grpclib.lib.MemberListService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.splan.xbet.grpclib.lib.MemberListRequest,
      com.splan.xbet.grpclib.lib.MemberListResponse> getMemberListMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "member_list",
      requestType = com.splan.xbet.grpclib.lib.MemberListRequest.class,
      responseType = com.splan.xbet.grpclib.lib.MemberListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.splan.xbet.grpclib.lib.MemberListRequest,
      com.splan.xbet.grpclib.lib.MemberListResponse> getMemberListMethod() {
    io.grpc.MethodDescriptor<com.splan.xbet.grpclib.lib.MemberListRequest, com.splan.xbet.grpclib.lib.MemberListResponse> getMemberListMethod;
    if ((getMemberListMethod = MemberListServiceGrpc.getMemberListMethod) == null) {
      synchronized (MemberListServiceGrpc.class) {
        if ((getMemberListMethod = MemberListServiceGrpc.getMemberListMethod) == null) {
          MemberListServiceGrpc.getMemberListMethod = getMemberListMethod =
              io.grpc.MethodDescriptor.<com.splan.xbet.grpclib.lib.MemberListRequest, com.splan.xbet.grpclib.lib.MemberListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "member_list"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.splan.xbet.grpclib.lib.MemberListRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.splan.xbet.grpclib.lib.MemberListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MemberListServiceMethodDescriptorSupplier("member_list"))
              .build();
        }
      }
    }
    return getMemberListMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MemberListServiceStub newStub(io.grpc.Channel channel) {
    return new MemberListServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MemberListServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MemberListServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MemberListServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MemberListServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class MemberListServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void memberList(com.splan.xbet.grpclib.lib.MemberListRequest request,
        io.grpc.stub.StreamObserver<com.splan.xbet.grpclib.lib.MemberListResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getMemberListMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getMemberListMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.splan.xbet.grpclib.lib.MemberListRequest,
                com.splan.xbet.grpclib.lib.MemberListResponse>(
                  this, METHODID_MEMBER_LIST)))
          .build();
    }
  }

  /**
   */
  public static final class MemberListServiceStub extends io.grpc.stub.AbstractStub<MemberListServiceStub> {
    private MemberListServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MemberListServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MemberListServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MemberListServiceStub(channel, callOptions);
    }

    /**
     */
    public void memberList(com.splan.xbet.grpclib.lib.MemberListRequest request,
        io.grpc.stub.StreamObserver<com.splan.xbet.grpclib.lib.MemberListResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMemberListMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MemberListServiceBlockingStub extends io.grpc.stub.AbstractStub<MemberListServiceBlockingStub> {
    private MemberListServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MemberListServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MemberListServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MemberListServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.splan.xbet.grpclib.lib.MemberListResponse memberList(com.splan.xbet.grpclib.lib.MemberListRequest request) {
      return blockingUnaryCall(
          getChannel(), getMemberListMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MemberListServiceFutureStub extends io.grpc.stub.AbstractStub<MemberListServiceFutureStub> {
    private MemberListServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MemberListServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MemberListServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MemberListServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.splan.xbet.grpclib.lib.MemberListResponse> memberList(
        com.splan.xbet.grpclib.lib.MemberListRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getMemberListMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_MEMBER_LIST = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MemberListServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MemberListServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_MEMBER_LIST:
          serviceImpl.memberList((com.splan.xbet.grpclib.lib.MemberListRequest) request,
              (io.grpc.stub.StreamObserver<com.splan.xbet.grpclib.lib.MemberListResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class MemberListServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MemberListServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.splan.xbet.grpclib.lib.MemberListSerbice.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MemberListService");
    }
  }

  private static final class MemberListServiceFileDescriptorSupplier
      extends MemberListServiceBaseDescriptorSupplier {
    MemberListServiceFileDescriptorSupplier() {}
  }

  private static final class MemberListServiceMethodDescriptorSupplier
      extends MemberListServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MemberListServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MemberListServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MemberListServiceFileDescriptorSupplier())
              .addMethod(getMemberListMethod())
              .build();
        }
      }
    }
    return result;
  }
}
