package com.shenhao.resp;

public class HttpResponseCode extends ResponseCode {
    public static final ResponseCode SUCCESS;
    public static final ResponseCode NO_CONTENT;
    public static final ResponseCode USER_LOGIN_FAIL;
    public static final ResponseCode USER_ACCESS_FORBIDDEN;
    public static final ResponseCode NOT_FOUND;
    public static final ResponseCode INTERNAL_ERROR;
    public static final ResponseCode SYSTEM_ERROR;
    public static final ResponseCode BUSINESS_SUCCESS;
    public static final ResponseCode BUSINESS_FAILURE;
    public static final ResponseCode UNKNOWN;
    public static final ResponseCode ACCESS_UNAUTHORIZED;
    public static final ResponseCode TOKEN_INVALID_OR_EXPIRED;
    public static final ResponseCode URL_UNAUTHORIZED;

    static {
        CODES.clear();
        SUCCESS = ResponseCode.of(200, "请求成功");
        NO_CONTENT = ResponseCode.of(204, "请求无内容");
        TOKEN_INVALID_OR_EXPIRED = ResponseCode.of(401, "token无效或已过期");
        USER_LOGIN_FAIL = ResponseCode.of(40101, "用户登录失败");
        USER_ACCESS_FORBIDDEN = ResponseCode.of(403, "用户授权失败");
        NOT_FOUND = ResponseCode.of(404, "资源不存在");
        ACCESS_UNAUTHORIZED = ResponseCode.of(405, "访问未授权");
        URL_UNAUTHORIZED = ResponseCode.of(406, "该地址禁止访问");
        INTERNAL_ERROR = ResponseCode.of(500, "服务器错误");
        SYSTEM_ERROR = ResponseCode.of(501, "系统繁忙");
        BUSINESS_SUCCESS = ResponseCode.of(1, "数据正常返回");
        BUSINESS_FAILURE = ResponseCode.of(-1, "业务异常");
        UNKNOWN = ResponseCode.of(-2, "未知系统异常");
    }

    public HttpResponseCode() {
    }
}