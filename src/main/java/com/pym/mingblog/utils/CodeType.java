package com.pym.mingblog.utils;

/**
 * @Author: Ming
 * @Date 2023/7/30 16:14
 * @Description: 状态码
 */
public enum CodeType {
    SUCCESS_STATUS(200, "请求成功"),
    FAIL_STATUS(400,"请求失败");


    private int code;
    private String message;

    CodeType(int code, String message) {
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
