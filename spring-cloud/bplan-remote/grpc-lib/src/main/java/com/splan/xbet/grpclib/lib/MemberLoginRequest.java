// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MemberLoginSerbice.proto

package com.splan.xbet.grpclib.lib;

/**
 * Protobuf type {@code com.splan.xbet.grpclib.lib.MemberLoginRequest}
 */
public  final class MemberLoginRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.splan.xbet.grpclib.lib.MemberLoginRequest)
    MemberLoginRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use MemberLoginRequest.newBuilder() to construct.
  private MemberLoginRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MemberLoginRequest() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new MemberLoginRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private MemberLoginRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            com.splan.xbet.grpclib.lib.Member.Builder subBuilder = null;
            if (member_ != null) {
              subBuilder = member_.toBuilder();
            }
            member_ = input.readMessage(com.splan.xbet.grpclib.lib.Member.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(member_);
              member_ = subBuilder.buildPartial();
            }

            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.splan.xbet.grpclib.lib.MemberLoginSerbice.internal_static_com_splan_xbet_grpclib_lib_MemberLoginRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.splan.xbet.grpclib.lib.MemberLoginSerbice.internal_static_com_splan_xbet_grpclib_lib_MemberLoginRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.splan.xbet.grpclib.lib.MemberLoginRequest.class, com.splan.xbet.grpclib.lib.MemberLoginRequest.Builder.class);
  }

  public static final int MEMBER_FIELD_NUMBER = 1;
  private com.splan.xbet.grpclib.lib.Member member_;
  /**
   * <code>.com.splan.xbet.grpclib.lib.Member member = 1;</code>
   */
  public boolean hasMember() {
    return member_ != null;
  }
  /**
   * <code>.com.splan.xbet.grpclib.lib.Member member = 1;</code>
   */
  public com.splan.xbet.grpclib.lib.Member getMember() {
    return member_ == null ? com.splan.xbet.grpclib.lib.Member.getDefaultInstance() : member_;
  }
  /**
   * <code>.com.splan.xbet.grpclib.lib.Member member = 1;</code>
   */
  public com.splan.xbet.grpclib.lib.MemberOrBuilder getMemberOrBuilder() {
    return getMember();
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (member_ != null) {
      output.writeMessage(1, getMember());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (member_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getMember());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.splan.xbet.grpclib.lib.MemberLoginRequest)) {
      return super.equals(obj);
    }
    com.splan.xbet.grpclib.lib.MemberLoginRequest other = (com.splan.xbet.grpclib.lib.MemberLoginRequest) obj;

    if (hasMember() != other.hasMember()) return false;
    if (hasMember()) {
      if (!getMember()
          .equals(other.getMember())) return false;
    }
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasMember()) {
      hash = (37 * hash) + MEMBER_FIELD_NUMBER;
      hash = (53 * hash) + getMember().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.splan.xbet.grpclib.lib.MemberLoginRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.splan.xbet.grpclib.lib.MemberLoginRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.splan.xbet.grpclib.lib.MemberLoginRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.splan.xbet.grpclib.lib.MemberLoginRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.splan.xbet.grpclib.lib.MemberLoginRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.splan.xbet.grpclib.lib.MemberLoginRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.splan.xbet.grpclib.lib.MemberLoginRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.splan.xbet.grpclib.lib.MemberLoginRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.splan.xbet.grpclib.lib.MemberLoginRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.splan.xbet.grpclib.lib.MemberLoginRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.splan.xbet.grpclib.lib.MemberLoginRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.splan.xbet.grpclib.lib.MemberLoginRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.splan.xbet.grpclib.lib.MemberLoginRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.splan.xbet.grpclib.lib.MemberLoginRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.splan.xbet.grpclib.lib.MemberLoginRequest)
      com.splan.xbet.grpclib.lib.MemberLoginRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.splan.xbet.grpclib.lib.MemberLoginSerbice.internal_static_com_splan_xbet_grpclib_lib_MemberLoginRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.splan.xbet.grpclib.lib.MemberLoginSerbice.internal_static_com_splan_xbet_grpclib_lib_MemberLoginRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.splan.xbet.grpclib.lib.MemberLoginRequest.class, com.splan.xbet.grpclib.lib.MemberLoginRequest.Builder.class);
    }

    // Construct using com.splan.xbet.grpclib.lib.MemberLoginRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (memberBuilder_ == null) {
        member_ = null;
      } else {
        member_ = null;
        memberBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.splan.xbet.grpclib.lib.MemberLoginSerbice.internal_static_com_splan_xbet_grpclib_lib_MemberLoginRequest_descriptor;
    }

    @java.lang.Override
    public com.splan.xbet.grpclib.lib.MemberLoginRequest getDefaultInstanceForType() {
      return com.splan.xbet.grpclib.lib.MemberLoginRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.splan.xbet.grpclib.lib.MemberLoginRequest build() {
      com.splan.xbet.grpclib.lib.MemberLoginRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.splan.xbet.grpclib.lib.MemberLoginRequest buildPartial() {
      com.splan.xbet.grpclib.lib.MemberLoginRequest result = new com.splan.xbet.grpclib.lib.MemberLoginRequest(this);
      if (memberBuilder_ == null) {
        result.member_ = member_;
      } else {
        result.member_ = memberBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.splan.xbet.grpclib.lib.MemberLoginRequest) {
        return mergeFrom((com.splan.xbet.grpclib.lib.MemberLoginRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.splan.xbet.grpclib.lib.MemberLoginRequest other) {
      if (other == com.splan.xbet.grpclib.lib.MemberLoginRequest.getDefaultInstance()) return this;
      if (other.hasMember()) {
        mergeMember(other.getMember());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.splan.xbet.grpclib.lib.MemberLoginRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.splan.xbet.grpclib.lib.MemberLoginRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.splan.xbet.grpclib.lib.Member member_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.splan.xbet.grpclib.lib.Member, com.splan.xbet.grpclib.lib.Member.Builder, com.splan.xbet.grpclib.lib.MemberOrBuilder> memberBuilder_;
    /**
     * <code>.com.splan.xbet.grpclib.lib.Member member = 1;</code>
     */
    public boolean hasMember() {
      return memberBuilder_ != null || member_ != null;
    }
    /**
     * <code>.com.splan.xbet.grpclib.lib.Member member = 1;</code>
     */
    public com.splan.xbet.grpclib.lib.Member getMember() {
      if (memberBuilder_ == null) {
        return member_ == null ? com.splan.xbet.grpclib.lib.Member.getDefaultInstance() : member_;
      } else {
        return memberBuilder_.getMessage();
      }
    }
    /**
     * <code>.com.splan.xbet.grpclib.lib.Member member = 1;</code>
     */
    public Builder setMember(com.splan.xbet.grpclib.lib.Member value) {
      if (memberBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        member_ = value;
        onChanged();
      } else {
        memberBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.com.splan.xbet.grpclib.lib.Member member = 1;</code>
     */
    public Builder setMember(
        com.splan.xbet.grpclib.lib.Member.Builder builderForValue) {
      if (memberBuilder_ == null) {
        member_ = builderForValue.build();
        onChanged();
      } else {
        memberBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.com.splan.xbet.grpclib.lib.Member member = 1;</code>
     */
    public Builder mergeMember(com.splan.xbet.grpclib.lib.Member value) {
      if (memberBuilder_ == null) {
        if (member_ != null) {
          member_ =
            com.splan.xbet.grpclib.lib.Member.newBuilder(member_).mergeFrom(value).buildPartial();
        } else {
          member_ = value;
        }
        onChanged();
      } else {
        memberBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.com.splan.xbet.grpclib.lib.Member member = 1;</code>
     */
    public Builder clearMember() {
      if (memberBuilder_ == null) {
        member_ = null;
        onChanged();
      } else {
        member_ = null;
        memberBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.com.splan.xbet.grpclib.lib.Member member = 1;</code>
     */
    public com.splan.xbet.grpclib.lib.Member.Builder getMemberBuilder() {
      
      onChanged();
      return getMemberFieldBuilder().getBuilder();
    }
    /**
     * <code>.com.splan.xbet.grpclib.lib.Member member = 1;</code>
     */
    public com.splan.xbet.grpclib.lib.MemberOrBuilder getMemberOrBuilder() {
      if (memberBuilder_ != null) {
        return memberBuilder_.getMessageOrBuilder();
      } else {
        return member_ == null ?
            com.splan.xbet.grpclib.lib.Member.getDefaultInstance() : member_;
      }
    }
    /**
     * <code>.com.splan.xbet.grpclib.lib.Member member = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.splan.xbet.grpclib.lib.Member, com.splan.xbet.grpclib.lib.Member.Builder, com.splan.xbet.grpclib.lib.MemberOrBuilder> 
        getMemberFieldBuilder() {
      if (memberBuilder_ == null) {
        memberBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.splan.xbet.grpclib.lib.Member, com.splan.xbet.grpclib.lib.Member.Builder, com.splan.xbet.grpclib.lib.MemberOrBuilder>(
                getMember(),
                getParentForChildren(),
                isClean());
        member_ = null;
      }
      return memberBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:com.splan.xbet.grpclib.lib.MemberLoginRequest)
  }

  // @@protoc_insertion_point(class_scope:com.splan.xbet.grpclib.lib.MemberLoginRequest)
  private static final com.splan.xbet.grpclib.lib.MemberLoginRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.splan.xbet.grpclib.lib.MemberLoginRequest();
  }

  public static com.splan.xbet.grpclib.lib.MemberLoginRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MemberLoginRequest>
      PARSER = new com.google.protobuf.AbstractParser<MemberLoginRequest>() {
    @java.lang.Override
    public MemberLoginRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new MemberLoginRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MemberLoginRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MemberLoginRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.splan.xbet.grpclib.lib.MemberLoginRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

