package com.tc.core.constant;

public enum ResEnum {

    SUCCESS(200, "请求成功"),
    UNKNOWN_ERROR(500, "未知错误"),
    BAD_REQUEST(400, "错误请求"),
    UNAUTHORIZED(401, "未授权"),
    PARAMETER_ERROR(406, "参数错误"),
    NOT_FOUND(404, "未发现");

    private final Integer code;

    private final String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
