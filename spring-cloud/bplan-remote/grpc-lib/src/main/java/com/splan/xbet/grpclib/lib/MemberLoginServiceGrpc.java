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
    comments = "Source: MemberLoginSerbice.proto")
public final class MemberLoginServiceGrpc {

  private MemberLoginServiceGrpc() {}

  public static final String SERVICE_NAME = "com.splan.xbet.grpclib.lib.MemberLoginService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.splan.xbet.grpclib.lib.MemberLoginRequest,
      com.splan.xbet.grpclib.lib.MemberLoginResponse> getMemberLoginMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "member_login",
      requestType = com.splan.xbet.grpclib.lib.MemberLoginRequest.class,
      responseType = com.splan.xbet.grpclib.lib.MemberLoginResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.splan.xbet.grpclib.lib.MemberLoginRequest,
      com.splan.xbet.grpclib.lib.MemberLoginResponse> getMemberLoginMethod() {
    io.grpc.MethodDescriptor<com.splan.xbet.grpclib.lib.MemberLoginRequest, com.splan.xbet.grpclib.lib.MemberLoginResponse> getMemberLoginMethod;
    if ((getMemberLoginMethod = MemberLoginServiceGrpc.getMemberLoginMethod) == null) {
      synchronized (MemberLoginServiceGrpc.class) {
        if ((getMemberLoginMethod = MemberLoginServiceGrpc.getMemberLoginMethod) == null) {
          MemberLoginServiceGrpc.getMemberLoginMethod = getMemberLoginMethod =
              io.grpc.MethodDescriptor.<com.splan.xbet.grpclib.lib.MemberLoginRequest, com.splan.xbet.grpclib.lib.MemberLoginResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "member_login"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.splan.xbet.grpclib.lib.MemberLoginRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.splan.xbet.grpclib.lib.MemberLoginResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MemberLoginServiceMethodDescriptorSupplier("member_login"))
              .build();
        }
      }
    }
    return getMemberLoginMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MemberLoginServiceStub newStub(io.grpc.Channel channel) {
    return new MemberLoginServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MemberLoginServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MemberLoginServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MemberLoginServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MemberLoginServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class MemberLoginServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void memberLogin(com.splan.xbet.grpclib.lib.MemberLoginRequest request,
        io.grpc.stub.StreamObserver<com.splan.xbet.grpclib.lib.MemberLoginResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getMemberLoginMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getMemberLoginMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.splan.xbet.grpclib.lib.MemberLoginRequest,
                com.splan.xbet.grpclib.lib.MemberLoginResponse>(
                  this, METHODID_MEMBER_LOGIN)))
          .build();
    }
  }

  /**
   */
  public static final class MemberLoginServiceStub extends io.grpc.stub.AbstractStub<MemberLoginServiceStub> {
    private MemberLoginServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MemberLoginServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MemberLoginServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MemberLoginServiceStub(channel, callOptions);
    }

    /**
     */
    public void memberLogin(com.splan.xbet.grpclib.lib.MemberLoginRequest request,
        io.grpc.stub.StreamObserver<com.splan.xbet.grpclib.lib.MemberLoginResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMemberLoginMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MemberLoginServiceBlockingStub extends io.grpc.stub.AbstractStub<MemberLoginServiceBlockingStub> {
    private MemberLoginServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MemberLoginServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MemberLoginServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MemberLoginServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.splan.xbet.grpclib.lib.MemberLoginResponse memberLogin(com.splan.xbet.grpclib.lib.MemberLoginRequest request) {
      return blockingUnaryCall(
          getChannel(), getMemberLoginMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MemberLoginServiceFutureStub extends io.grpc.stub.AbstractStub<MemberLoginServiceFutureStub> {
    private MemberLoginServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MemberLoginServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MemberLoginServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MemberLoginServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.splan.xbet.grpclib.lib.MemberLoginResponse> memberLogin(
        com.splan.xbet.grpclib.lib.MemberLoginRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getMemberLoginMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_MEMBER_LOGIN = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MemberLoginServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MemberLoginServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_MEMBER_LOGIN:
          serviceImpl.memberLogin((com.splan.xbet.grpclib.lib.MemberLoginRequest) request,
              (io.grpc.stub.StreamObserver<com.splan.xbet.grpclib.lib.MemberLoginResponse>) responseObserver);
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

  private static abstract class MemberLoginServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MemberLoginServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.splan.xbet.grpclib.lib.MemberLoginSerbice.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MemberLoginService");
    }
  }

  private static final class MemberLoginServiceFileDescriptorSupplier
      extends MemberLoginServiceBaseDescriptorSupplier {
    MemberLoginServiceFileDescriptorSupplier() {}
  }

  private static final class MemberLoginServiceMethodDescriptorSupplier
      extends MemberLoginServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MemberLoginServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (MemberLoginServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MemberLoginServiceFileDescriptorSupplier())
              .addMethod(getMemberLoginMethod())
              .build();
        }
      }
    }
    return result;
  }
}
