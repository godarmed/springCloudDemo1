package com.springcloud.global.exception.error;
public class BaseHandlerException extends RuntimeException implements IException {
    private static final long serialVersionUID = 1L;
    private int code = 0;
    private String message = null;

    public BaseHandlerException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"code\":").append(this.code).append(" ,message: \"").append(this.message).append("\"}");
        return sb.toString();
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

