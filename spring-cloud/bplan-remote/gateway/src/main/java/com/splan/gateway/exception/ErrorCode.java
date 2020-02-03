package com.splan.gateway.exception;

/**
 * @author keets
 */
public class ErrorCode {

    private final int code;
    private final String message;
    private final String detailMessage;
    private boolean success;

    public ErrorCode(int code, String message, String detailMessage) {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public ErrorCode(boolean success,int code, String message, String detailMessage) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public ErrorCode(String message, String detailMessage) {
        this.code = 0;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
