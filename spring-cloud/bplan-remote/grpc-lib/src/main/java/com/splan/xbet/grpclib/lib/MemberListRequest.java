// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MemberListSerbice.proto

package com.splan.xbet.grpclib.lib;

/**
 * Protobuf type {@code com.splan.xbet.grpclib.lib.MemberListRequest}
 */
public  final class MemberListRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.splan.xbet.grpclib.lib.MemberListRequest)
    MemberListRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use MemberListRequest.newBuilder() to construct.
  private MemberListRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MemberListRequest() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new MemberListRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private MemberListRequest(
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
          case 8: {

            page_ = input.readInt32();
            break;
          }
          case 16: {

            perPage_ = input.readInt32();
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
    return com.splan.xbet.grpclib.lib.MemberListSerbice.internal_static_com_splan_xbet_grpclib_lib_MemberListRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.splan.xbet.grpclib.lib.MemberListSerbice.internal_static_com_splan_xbet_grpclib_lib_MemberListRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.splan.xbet.grpclib.lib.MemberListRequest.class, com.splan.xbet.grpclib.lib.MemberListRequest.Builder.class);
  }

  public static final int PAGE_FIELD_NUMBER = 1;
  private int page_;
  /**
   * <code>int32 page = 1;</code>
   */
  public int getPage() {
    return page_;
  }

  public static final int PER_PAGE_FIELD_NUMBER = 2;
  private int perPage_;
  /**
   * <code>int32 per_page = 2;</code>
   */
  public int getPerPage() {
    return perPage_;
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
    if (page_ != 0) {
      output.writeInt32(1, page_);
    }
    if (perPage_ != 0) {
      output.writeInt32(2, perPage_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (page_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, page_);
    }
    if (perPage_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, perPage_);
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
    if (!(obj instanceof com.splan.xbet.grpclib.lib.MemberListRequest)) {
      return super.equals(obj);
    }
    com.splan.xbet.grpclib.lib.MemberListRequest other = (com.splan.xbet.grpclib.lib.MemberListRequest) obj;

    if (getPage()
        != other.getPage()) return false;
    if (getPerPage()
        != other.getPerPage()) return false;
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
    hash = (37 * hash) + PAGE_FIELD_NUMBER;
    hash = (53 * hash) + getPage();
    hash = (37 * hash) + PER_PAGE_FIELD_NUMBER;
    hash = (53 * hash) + getPerPage();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.splan.xbet.grpclib.lib.MemberListRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.splan.xbet.grpclib.lib.MemberListRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.splan.xbet.grpclib.lib.MemberListRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.splan.xbet.grpclib.lib.MemberListRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.splan.xbet.grpclib.lib.MemberListRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.splan.xbet.grpclib.lib.MemberListRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.splan.xbet.grpclib.lib.MemberListRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.splan.xbet.grpclib.lib.MemberListRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.splan.xbet.grpclib.lib.MemberListRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.splan.xbet.grpclib.lib.MemberListRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.splan.xbet.grpclib.lib.MemberListRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.splan.xbet.grpclib.lib.MemberListRequest parseFrom(
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
  public static Builder newBuilder(com.splan.xbet.grpclib.lib.MemberListRequest prototype) {
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
   * Protobuf type {@code com.splan.xbet.grpclib.lib.MemberListRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.splan.xbet.grpclib.lib.MemberListRequest)
      com.splan.xbet.grpclib.lib.MemberListRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.splan.xbet.grpclib.lib.MemberListSerbice.internal_static_com_splan_xbet_grpclib_lib_MemberListRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.splan.xbet.grpclib.lib.MemberListSerbice.internal_static_com_splan_xbet_grpclib_lib_MemberListRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.splan.xbet.grpclib.lib.MemberListRequest.class, com.splan.xbet.grpclib.lib.MemberListRequest.Builder.class);
    }

    // Construct using com.splan.xbet.grpclib.lib.MemberListRequest.newBuilder()
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
      page_ = 0;

      perPage_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.splan.xbet.grpclib.lib.MemberListSerbice.internal_static_com_splan_xbet_grpclib_lib_MemberListRequest_descriptor;
    }

    @java.lang.Override
    public com.splan.xbet.grpclib.lib.MemberListRequest getDefaultInstanceForType() {
      return com.splan.xbet.grpclib.lib.MemberListRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.splan.xbet.grpclib.lib.MemberListRequest build() {
      com.splan.xbet.grpclib.lib.MemberListRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.splan.xbet.grpclib.lib.MemberListRequest buildPartial() {
      com.splan.xbet.grpclib.lib.MemberListRequest result = new com.splan.xbet.grpclib.lib.MemberListRequest(this);
      result.page_ = page_;
      result.perPage_ = perPage_;
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
      if (other instanceof com.splan.xbet.grpclib.lib.MemberListRequest) {
        return mergeFrom((com.splan.xbet.grpclib.lib.MemberListRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.splan.xbet.grpclib.lib.MemberListRequest other) {
      if (other == com.splan.xbet.grpclib.lib.MemberListRequest.getDefaultInstance()) return this;
      if (other.getPage() != 0) {
        setPage(other.getPage());
      }
      if (other.getPerPage() != 0) {
        setPerPage(other.getPerPage());
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
      com.splan.xbet.grpclib.lib.MemberListRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.splan.xbet.grpclib.lib.MemberListRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int page_ ;
    /**
     * <code>int32 page = 1;</code>
     */
    public int getPage() {
      return page_;
    }
    /**
     * <code>int32 page = 1;</code>
     */
    public Builder setPage(int value) {
      
      page_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 page = 1;</code>
     */
    public Builder clearPage() {
      
      page_ = 0;
      onChanged();
      return this;
    }

    private int perPage_ ;
    /**
     * <code>int32 per_page = 2;</code>
     */
    public int getPerPage() {
      return perPage_;
    }
    /**
     * <code>int32 per_page = 2;</code>
     */
    public Builder setPerPage(int value) {
      
      perPage_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 per_page = 2;</code>
     */
    public Builder clearPerPage() {
      
      perPage_ = 0;
      onChanged();
      return this;
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


    // @@protoc_insertion_point(builder_scope:com.splan.xbet.grpclib.lib.MemberListRequest)
  }

  // @@protoc_insertion_point(class_scope:com.splan.xbet.grpclib.lib.MemberListRequest)
  private static final com.splan.xbet.grpclib.lib.MemberListRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.splan.xbet.grpclib.lib.MemberListRequest();
  }

  public static com.splan.xbet.grpclib.lib.MemberListRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MemberListRequest>
      PARSER = new com.google.protobuf.AbstractParser<MemberListRequest>() {
    @java.lang.Override
    public MemberListRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new MemberListRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MemberListRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MemberListRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.splan.xbet.grpclib.lib.MemberListRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
