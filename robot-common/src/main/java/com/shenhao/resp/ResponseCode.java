package com.shenhao.resp;

import java.util.HashSet;
import java.util.Set;

public class ResponseCode {
    protected static final Set<Integer> CODES = new HashSet<>();
    public static final ResponseCode SUCCEED = of(1, "成功");
    public static final ResponseCode FAIL = of(0, "失败");
    private int code;
    private String msg;

    public ResponseCode() {
    }

    protected static ResponseCode of(int code, String msg) {
        if (!CODES.add(code)) {
            throw new IllegalArgumentException(String.format("响应状态码已存在: '%s'", code));
        } else {
            ResponseCode response = new ResponseCode();
            response.code = code;
            response.msg = msg;
            return response;
        }
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public boolean isSucceed() {
        return this.code == SUCCEED.code;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ResponseCode)) {
            return false;
        } else {
            ResponseCode c = (ResponseCode) obj;
            return c.getCode() == this.code;
        }
    }

    @Override
    public String toString() {
        return "[" + this.code + "," + this.msg + "]";
    }
}