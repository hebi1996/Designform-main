package com.design.common.utils;

public enum ResultCode {
    SUCCESS(0, "成功"),
    FAILURE(-1, "失败"),
    ACCOUNT_PWD_ERROR(900, "账号或者密码错误"),
    REQUEST_TIMEOUT_ERROR(550,"请求超时");

    private int code;
    private String message;

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
